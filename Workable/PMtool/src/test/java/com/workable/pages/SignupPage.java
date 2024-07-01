package com.workable.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SignupPage {
    private WebDriver driver;

    private By nameField = By.id("fullName");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By companyField = By.id("company");
    private By addressField = By.id("address");
    private By signup = By.cssSelector("button[type='submit']");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String timestamp = LocalDateTime.now().format(formatter);

    public SignupPage(WebDriver driver) {

        this.driver = driver;
    }


    public void register(String name, String email, String password, String company, String address) {
        String updatedEmail = name + timestamp + email;
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(updatedEmail);
        driver.findElement(passwordField).sendKeys(password);
        if (company != null) {
            driver.findElement(companyField).sendKeys(company);
        }
        if (address != null) {
            driver.findElement(addressField).sendKeys(address);
        }
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public boolean isRegistrationSuccessful() {
        // Implement check for successful registration, e.g., URL change, message display
        return true;
    }
}
