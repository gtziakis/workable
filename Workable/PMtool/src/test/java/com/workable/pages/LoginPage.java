package com.workable.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private WebDriver driver;

    // Locators
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By logoutButton = By.id("logout"); // Assuming logout element ID


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setEmail(String email) {
        WebElement emailInput = driver.findElement(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        WebElement passwordInput = driver.findElement(passwordField);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLogin();
    }

    public boolean isLoginSuccessful() {
        return driver.findElements(logoutButton).size() > 0;
    }

}

