package com.workable.tests;

import com.workable.config.User;
import com.workable.pages.BaseTest;
import com.workable.pages.LoginPage;
import com.workable.pages.PMtoolPage;
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

public class ProjectManagementTest extends BaseTest {
    private List<User> users;
    private static final Logger logger = LoggerFactory.getLogger(UserManagementTest.class);

    @BeforeClass
    public void loadTestData() {
        users = new ArrayList<>();

        // Existing user
        users.add(new User("existing", "User4", "user4@example.com", "password", "Company4", "Address4", "project4", "projectDescription4", "Summary1", "Description1", "TO DO", "backend"));
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
    public void testCreateProjectWithEmptyValues(User user) throws InterruptedException {
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


        logger.info("Adding project with empty fields");
        PMtoolPage pmToolPage = new PMtoolPage(driver);
        pmToolPage.createProject();
        logger.info("Check error message for empty project name");
        boolean projectNameEmpty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'invalid-feedback') and text()='This field is required']"))).isDisplayed();
        Assert.assertTrue(projectNameEmpty);
        logger.info("Check error message for empty project description");
        boolean projectDescriptionEmpty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'invalid-feedback') and text()='This field is required']"))).isDisplayed();
        Assert.assertTrue(projectDescriptionEmpty);
        logger.info("Return to dashboard");
        pmToolPage.goToDashboard();

        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();
    }
    @Test(dataProvider = "userDataProvider",priority = 2)
    public void testCreateProject(User user)  {
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
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();

    }

    @Test(dataProvider = "userDataProvider",priority = 3)
    public void testViewProject(User user)  {
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
        pmToolPage.viewProject(user.getProjectName());
        boolean projectDescriptionViewed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='"+ user.getProjectDescription()+"']"))).isDisplayed();
        Assert.assertTrue(projectDescriptionViewed);
        boolean projectNameViewed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='"+ user.getProjectName()+"']"))).isDisplayed();
        Assert.assertTrue(projectNameViewed);
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();
    }


    @Test(dataProvider = "userDataProvider",priority = 4)
    public void testUpdateProject(User user) throws InterruptedException {
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


        logger.info("Updating project");
        PMtoolPage pmToolPage = new PMtoolPage(driver);
        pmToolPage.updateProject("project4_updated", "Updated Description");
        boolean projectDescriptionUpdated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Updated Description']"))).isDisplayed();
        Assert.assertTrue(projectDescriptionUpdated);
        boolean projectNameUpdated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'_updated')]"))).isDisplayed();
        Assert.assertTrue(projectNameUpdated);
        logger.info("Project updated successfully");
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();
    }

    @Test(dataProvider = "userDataProvider",priority = 5)
    public void testDeleteProject(User user)  {
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


        logger.info("Deleting project" + user.getProjectName()+"_updated");
        PMtoolPage pmToolPage = new PMtoolPage(driver);
        pmToolPage.deleteProject(user.getProjectName()+"_updated");
        boolean deleteOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete_project"))).isDisplayed();
        Assert.assertTrue(deleteOption);
        logger.info("*******************SUCCESS***********************");
        pmToolPage.logout();

    }

}



