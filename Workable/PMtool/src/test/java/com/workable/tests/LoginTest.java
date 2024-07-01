package com.workable.tests;

import com.workable.config.User;
import com.workable.pages.BaseTest;
import com.workable.pages.LoginPage;
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

public class LoginTest extends BaseTest {
    private List<User> users;

    private static final Logger logger = LoggerFactory.getLogger(UserManagementTest.class);

    @BeforeClass
    public void loadTestData() {
        users = new ArrayList<>();

        // Existing user
        users.add(new User("existing", "User4", "user4@example.com", "password", "Company4", "Address4"));

        //Invalid login info
        users.add(new User("invalid", "User4", "", "", "Company4", "Address4"));

    }

    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() {
        Object[][] data = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i);
        }
        return data;
    }
    @Test(dataProvider = "userDataProvider")
    public void testLoginUser(User user)  {
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

        if ("existing".equals(user.getDescription())) {
            boolean logoutButtonVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout"))).isDisplayed();
            Assert.assertTrue(logoutButtonVisible, "Logout button is not visible, login may have failed.");
            logger.info("*******************SUCCESS***********************");
        }

        else if ("invalid".equals(user.getDescription())) {

            By failureMessageLocatorEmail = By.xpath("//*[contains(@class, 'invalid-feedback')]/../label[@for='email']");
            By failureMessageLocatorPassword = By.xpath("//*[contains(@class, 'invalid-feedback')]/../label[@for='password']");

            //  Check error message for email
            logger.info("Checking error messages for invalid email");
            WebElement failureMessageEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocatorEmail));
            Assert.assertTrue(failureMessageEmail.isDisplayed(), "Invalid login info");
            //  Check error message for password
            logger.info("Checking error messages for invalid email");
            WebElement failureMessagePassword = wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocatorPassword));
            Assert.assertTrue(failureMessagePassword.isDisplayed(), "Invalid login info");
            logger.info("*******************SUCCESS***********************");
        }

    }
}




