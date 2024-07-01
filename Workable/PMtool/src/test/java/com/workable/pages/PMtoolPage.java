package com.workable.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PMtoolPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for Project options
    private By createProjectButton = By.xpath("//a[text()='Create']");
    private By updateProjectButton = By.id("btn_update_project");
    private By deleteProjectButton = By.id("delete_project");

    // Locators for Task options
    private By addTaskButton = By.id("btn_add_task");
    private By viewTasksButton = By.id("btn_view_tasks");

    // Locators for menu options
    private By logoutButton = By.id("logout");
    private By dashboardLink = By.id("dashboard");


    // Constructor
    public PMtoolPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to create a project
    public void createProject(String projectName, String projectDescription) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createProjectButton)).click();
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement descriptionField = driver.findElement(By.id("description"));
        WebElement createButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameField.sendKeys(projectName);
        descriptionField.sendKeys(projectDescription);
        createButton.click();
    }

    // Method to create an empty project
    public void createProject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createProjectButton)).click();
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement descriptionField = driver.findElement(By.id("description"));
        WebElement createButton = driver.findElement(By.cssSelector("button[type='submit']"));

        createButton.click();
    }

    // Method to update a project
    public void updateProject(String projectName, String newDescription) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateProjectButton)).click();
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement descriptionField = driver.findElement(By.id("description"));
        WebElement updateButton = driver.findElement(By.cssSelector("button[type='submit']"));
        Thread.sleep(1000);
        nameField.clear();
        Thread.sleep(1000);
        nameField.sendKeys(projectName);
        descriptionField.clear();
        Thread.sleep(1000);
        descriptionField.sendKeys(newDescription);
        Thread.sleep(1000);
        updateButton.click();
    }

    // Method to delete a project
    public void deleteProject(String projectName) {
        try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteProjectButton)).click();
        // Locate the delete button for the specified project
        WebElement deleteProjectButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+projectName+"']/ancestor::div//a[@id='delete_project']")));
        deleteProjectButton.click();
        // Handle the alert if present
        handleAlert();

        // Optionally wait for the alert to disappear after confirmation
        wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
    } catch (UnhandledAlertException uae) {
        // Handle UnhandledAlertException explicitly
        System.out.println("UnhandledAlertException occurred. Handling the alert...");
            WebElement deleteProjectButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+projectName+"']/ancestor::div//a[@id='delete_project']")));
            deleteProjectButton.click();
        handleAlert();
            wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
    } catch (Exception e) {
        // Handle other exceptions if needed
        e.printStackTrace();
    }

    }
    // Method to handle the alert dialog
    private void handleAlert() {
        try {
            // Wait for the alert to be present
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            Thread.sleep(1000);
            alert.accept(); // Accept the alert (click OK/Yes)
        } catch (Exception e) {
            System.out.println("Failed to handle alert: " + e.getMessage());
        }
    }

    // Method to view  a project without making any changes
    public void viewProject(String projectName) {
        // Navigate to the project view page
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateProjectButton)).click();
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement descriptionField = driver.findElement(By.id("description"));

    }

    // Method to navigate to Dashboard
    public void goToDashboard()  {

       wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLink)).click();
    }

    // Method to logout
    public void logout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).click();
    }

}

