package com.workable.tests;

import com.workable.config.User;
import com.workable.pages.BaseTest;
import com.workable.pages.LoginPage;
import com.workable.pages.PMtoolPage;
import com.workable.pages.TaskPage;
import org.openqa.selenium.By;
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

public class TaskManagementTest extends BaseTest {

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
    public void testCreateTaskWithEmptyValues(User user) throws InterruptedException {
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
        taskPage.createEmptyTask();

        logger.info("Check error message for empty task summary");
        boolean projectDescriptionEmpty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'invalid-feedback') and text()='This field is required']"))).isDisplayed();
        Assert.assertTrue(projectDescriptionEmpty);
        logger.info("Check error message for empty task description");
        boolean projectNameEmpty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'invalid-feedback') and text()='This field is required']"))).isDisplayed();
        Assert.assertTrue(projectNameEmpty);
        logger.info("Return to dashboard");
        pmToolPage.goToDashboard();
        pmToolPage.deleteProject(user.getProjectName());


        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();
    }

    @Test(dataProvider = "userDataProvider",priority = 2)
    public void testCreateTask(User user) {
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
          logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();

    }

    @Test(dataProvider = "userDataProvider",priority = 3)
    public void testViewTask(User user) throws InterruptedException {
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
        logger.info("Checking existing task" + user.getTaskSummary());
        TaskPage taskPage = new TaskPage(driver);
        taskPage.viewTasks();
        Thread.sleep(1000);
        taskPage.viewTask(user.getTaskSummary());
        boolean TaskSummaryDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='"+user.getTaskSummary()+"']"))).isDisplayed();
        Assert.assertTrue(TaskSummaryDisplayed);
        boolean TaskDescriptionDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[text()='"+user.getTaskDescription()+"']"))).isDisplayed();
        Assert.assertTrue(TaskDescriptionDisplayed);
        boolean TaskLabelDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+user.getTaskLabel()+"']"))).isDisplayed();
        Assert.assertTrue(TaskLabelDisplayed);
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();

    }



    @Test(dataProvider = "userDataProvider",priority = 4)
    public void testUpdateTask(User user) throws InterruptedException {
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
        logger.info("Checking existing task" + user.getTaskSummary());
        TaskPage taskPage = new TaskPage(driver);
        taskPage.viewTasks();
        Thread.sleep(1000);
        taskPage.viewTask(user.getTaskSummary());
        taskPage.updateTask(user.getTaskSummary()+"_updated", "Updated Description");

        boolean taskSummaryUpdated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+user.getTaskSummary()+"_updated"+"')]"))).isDisplayed();
        Assert.assertTrue(taskSummaryUpdated);
        boolean taskDescriptionUpdated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Updated Description']"))).isDisplayed();
        Assert.assertTrue(taskDescriptionUpdated);
        logger.info("Task updated successfully");
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();
    }

    @Test(dataProvider = "userDataProvider",priority = 5)
    public void testDeleteTask(User user) throws InterruptedException {
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
        logger.info("Checking existing task" + user.getTaskSummary());
        TaskPage taskPage = new TaskPage(driver);
        taskPage.viewTasks();
        Thread.sleep(1000);

        taskPage.deleteTask(user.getTaskSummary()+"_updated");
        boolean taskDeleted = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(),'"+user.getTaskSummary()+"_updated"+"')]")));
        Assert.assertTrue(taskDeleted);
        logger.info("Task deleted successfully");
        pmToolPage.goToDashboard();
        Thread.sleep(2000);
        pmToolPage.deleteProject(user.getProjectName());
        logger.info("Project deleted successfully");
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();
    }
}
