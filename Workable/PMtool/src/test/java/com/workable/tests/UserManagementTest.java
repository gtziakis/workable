package com.workable.tests;


import com.workable.config.User;
import com.workable.pages.BaseTest;
import com.workable.pages.SignupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.Assert;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagementTest extends BaseTest {
    private List<User> users;
    private static final Logger logger = LoggerFactory.getLogger(UserManagementTest.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
    String timestamp = LocalDateTime.now().format(formatter);

    @BeforeClass
    public void loadTestData() {
        users = new ArrayList<>();

        // User with normal values
        users.add(new User("normal", "User1", "user1" +timestamp+"@example.com", "password", "Company1", "Address1"));

        // User with missing required fields
        users.add(new User("missing", "", "", "", "Company2", "Address2"));

        // User with invalid email
        users.add(new User("invalid", "User3", "#example", "password", "Company3", "Address3"));

        // Existing user
        users.add(new User("existing", "User4", "user4@example.com", "password", "Company4", "Address4"));
    }

    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() {
        // Filter out users with description "existing"
        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (!"existing".equals(user.getDescription())) {
                filteredUsers.add(user);
            }
        }

        Object[][] data = new Object[filteredUsers.size()][1]; // Use filteredUsers.size() here
        for (int i = 0; i < filteredUsers.size(); i++) { // Iterate over filteredUsers
            data[i][0] = filteredUsers.get(i); // Use filteredUsers
        }
        return data;
    }

    @Test(dataProvider = "userDataProvider")
    public void testSignUpUserRegistration(User user)  {
        logger.info("Starting Selenium test...");

        SignupPage signupPage = new SignupPage(driver);
        logger.info("Pressing Sign Up");
        driver.findElement(By.id("signup")).click();
        logger.info("Registering User");
        signupPage.register(
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getCompany(),
                user.getAddress()
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        if ("missing".equals(user.getDescription())) {
            By failureMessageLocatorFullName = By.xpath("//*[contains(@class, 'invalid-feedback')]/../label[@for='fullName']");
            By failureMessageLocatorEmail = By.xpath("//*[contains(@class, 'invalid-feedback')]/../label[@for='email']");
            By failureMessageLocatorPassword = By.xpath("//*[contains(@class, 'invalid-feedback')]/../label[@for='password']");

            // Check error message for fullName
            logger.info("Checking error messages for missing data");
            WebElement failureMessageFullName = wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocatorFullName));
            Assert.assertTrue(failureMessageFullName.isDisplayed(), "This field is required");
            // Check error message for email
            WebElement failureMessageEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocatorEmail));
            Assert.assertTrue(failureMessageEmail.isDisplayed(), "This field is required");
            // Check error message for password
            WebElement failureMessagePassword = wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocatorPassword));
            Assert.assertTrue(failureMessagePassword.isDisplayed(), "This field is required");
            logger.info("*******************SUCCESS***********************");
        }

        else if ("invalid".equals(user.getDescription())) {

            By failureMessageLocatorEmail = By.xpath("//*[contains(@class, 'invalid-feedback')]/../label[@for='email']");

            //  Check error message for email
            logger.info("Checking error messages for invalid email");
            WebElement failureMessageEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocatorEmail));
            Assert.assertTrue(failureMessageEmail.isDisplayed(), "Invalid email format");
            logger.info("*******************SUCCESS***********************");
        }

        else if ("normal".equals(user.getDescription())) {
            logger.info("Checking successful registration");
            Assert.assertTrue(signupPage.isRegistrationSuccessful(), "Registration should succeed for valid data.");
            logger.info("*******************SUCCESS***********************");
        }
    }


}