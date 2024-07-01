package com.workable.tests;

import com.workable.config.User;
import com.workable.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TaskDBOperationTest extends BaseTest {
    private List<User> users;
    private static final Logger logger = LoggerFactory.getLogger(UserManagementTest.class);

    @BeforeClass
    public void loadTestData() {
        users = new ArrayList<>();

        // Existing user
        users.add(new User("existing", "User4", "user4@example.com", "password", "Company4", "Address4", "project4", "projectDescription4", "Summary1", "Description1", "DONE", "backend"));
    }
    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() {
        Object[][] data = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i);
        }
        return data;
    }
    @Test(dataProvider = "userDataProvider",priority = 1)
    public void testCreateTask(User user){
        logger.info("Starting Selenium test...");

        LoginPage loginPage = new LoginPage(driver);
        logger.info("Pressing Login");
        driver.findElement(By.id("login")).click();
        logger.info("Logging in User");
        loginPage.login(
                user.getEmail(),
                user.getPassword()
        );

        logger.info("Checking successful login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean logoutButtonVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout"))).isDisplayed();
        Assert.assertTrue(logoutButtonVisible);


        logger.info("Creating project" + user.getProjectName());
        PMtoolPage pmToolPage = new PMtoolPage(driver);
        pmToolPage.createProject(user.getProjectName(), user.getProjectDescription());
        boolean ProjectCreated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+user.getProjectName()+"']"))).isDisplayed();
        Assert.assertTrue(ProjectCreated);

        logger.info("Creating the task" + user.getTaskSummary());
        TaskPage taskPage = new TaskPage(driver);
        taskPage.createTask(user.getTaskSummary(),user.getTaskDescription(),user.getTaskStatus(), user.getTaskLabel());
        boolean TaskSummaryDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+user.getTaskSummary()+"']"))).isDisplayed();
        Assert.assertTrue(TaskSummaryDisplayed);
        boolean TaskDescriptionDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='"+user.getTaskDescription()+"']"))).isDisplayed();
        Assert.assertTrue(TaskDescriptionDisplayed);
        pmToolPage.goToDashboard();
        taskPage.createTask("Summary2","Description2","TO DO", "frontend");
        boolean TaskSummaryDisplayed2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Summary2']"))).isDisplayed();
        Assert.assertTrue(TaskSummaryDisplayed2);
        boolean TaskDescriptionDisplayed2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Description2']"))).isDisplayed();
        Assert.assertTrue(TaskDescriptionDisplayed2);
        pmToolPage.goToDashboard();
        taskPage.createTask("Summary3","Description3","TO DO", "backend");
        pmToolPage.goToDashboard();
        boolean TaskSummaryDisplayed3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Summary3']"))).isDisplayed();
        Assert.assertTrue(TaskSummaryDisplayed3);
        boolean TaskDescriptionDisplayed3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Description3']"))).isDisplayed();
        Assert.assertTrue(TaskDescriptionDisplayed3);

        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();

    }
    @Test(dataProvider = "userDataProvider",priority = 2)
    public void testSearchTasks(User user) throws InterruptedException {
        logger.info("Starting Selenium test...");

        LoginPage loginPage = new LoginPage(driver);
        logger.info("Pressing Login");
        driver.findElement(By.id("login")).click();
        logger.info("Logging in User");
        loginPage.login(
                user.getEmail(),
                user.getPassword()
        );

        logger.info("Checking successful login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean logoutButtonVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout"))).isDisplayed();
        Assert.assertTrue(logoutButtonVisible);


        logger.info("View existing project" + user.getProjectName());
        PMtoolPage pmToolPage = new PMtoolPage(driver);
        logger.info("Checking existing tasks");
        TaskPage taskPage = new TaskPage(driver);
        logger.info("Opening TaskDB");
        taskPage.openTaskDB();
        TaskDBPage taskdbPage = new TaskDBPage(driver);
        taskdbPage.searchTaskBySummary("Summary2");
        Thread.sleep(1000);
        int taskCount =taskdbPage.getTaskCountBySummary("Summary2");
        Assert.assertEquals(taskCount, 1);
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();

    }
    @Test(dataProvider = "userDataProvider",priority = 3)
    public void testSortTasks(User user) throws InterruptedException {
        logger.info("Starting Selenium test...");

        LoginPage loginPage = new LoginPage(driver);
        logger.info("Pressing Login");
        driver.findElement(By.id("login")).click();
        logger.info("Logging in User");
        loginPage.login(
                user.getEmail(),
                user.getPassword()
        );

        logger.info("Checking successful login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean logoutButtonVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout"))).isDisplayed();
        Assert.assertTrue(logoutButtonVisible);


        logger.info("View existing project" + user.getProjectName());
        PMtoolPage pmToolPage = new PMtoolPage(driver);
        logger.info("Checking existing tasks");
        TaskPage taskPage = new TaskPage(driver);
        logger.info("Opening TaskDB");
        taskPage.openTaskDB();
        TaskDBPage taskdbPage = new TaskDBPage(driver);
        // Sort tasks in ascending order
        taskdbPage.sortTasksBySummary(true);
        String firstTaskAsc = taskdbPage.getFirstTaskSummary();
        String lastTaskAsc = taskdbPage.getLastTaskSummary();
        // Print first and last task summaries after ascending sort
        System.out.println("First Task (Ascending): " + firstTaskAsc);
        System.out.println("Last Task (Ascending): " + lastTaskAsc);

        // Sort tasks in descending order
        taskdbPage.sortTasksBySummary(false);
        String firstTaskDesc = taskdbPage.getFirstTaskSummary();
        String lastTaskDesc = taskdbPage.getLastTaskSummary();

        // Print first and last task summaries after descending sort
        System.out.println("First Task (Descending): " + firstTaskDesc);
        System.out.println("Last Task (Descending): " + lastTaskDesc);

        // Example assertions (modify as per actual expected values)
        Assert.assertNotEquals(firstTaskAsc, lastTaskAsc, "First and last tasks should not be the same in ascending order.");
        Assert.assertNotEquals(firstTaskDesc, lastTaskDesc, "First and last tasks should not be the same in descending order.");
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();

    }
}
