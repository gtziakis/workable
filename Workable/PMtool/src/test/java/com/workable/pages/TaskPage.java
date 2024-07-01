package com.workable.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TaskPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By loginEmailField = By.id("login_email");
    private By loginPasswordField = By.id("login_password");
    private By loginButton = By.id("login_button");

    private By createProjectButton = By.xpath("//a[text()='Create']");

    private By projectNameField = By.id("project_name");
    private By projectDescriptionField = By.id("project_description");
    private By saveProjectButton = By.id("save_project_button");
    // Locators for Task options
    private By createTaskButton = By.id("btn_add_task");
    private By viewTasksButton = By.id("btn_view_tasks");

    private By taskNameField = By.id("summary");
    private By taskDescriptionField = By.id("description");

    private By taskStatusField =   By.xpath("//input[@class='select-dropdown dropdown-trigger']");


    private By taskLabelField = By.id("search_input");

    private By fileUploadField = By.xpath("//input[@placeholder='Upload file']");
    private By saveTaskButton = By.xpath("//button[@type='submit']");


    private By deleteTaskButton = By.xpath("//a[@id='btn_delete_task']");

    // Locators for menu options
    private By logoutButton = By.id("logout");
    private By dashboardLink = By.id("dashboard");
    private By taskDBLink = By.id("task_db");
    private By settingsLink = By.id("settings");

    // Constructor
    public TaskPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    // Method to view tasks for a project
    public void viewTasks() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewTasksButton)).click();
    }

    // Method to create an empty task
    public void createEmptyTask() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createTaskButton)).click();
        driver.findElement(saveTaskButton).click(); // Save without entering any task details
    }

    // Method to create a task
    public void createTask(String taskName, String taskDescription) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createTaskButton)).click();
        driver.findElement(taskNameField).sendKeys(taskName);
        driver.findElement(taskDescriptionField).sendKeys(taskDescription);
        driver.findElement(saveTaskButton).click();
    }
    public void createTask(String taskName, String taskDescription, String taskStatus, String taskLabel) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(createTaskButton)).click();
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(taskNameField));
        wait.until(ExpectedConditions.elementToBeClickable(nameField)).sendKeys(taskName);

        // Wait until the task description field is visible and interactable
        WebElement descriptionField = wait.until(ExpectedConditions.visibilityOfElementLocated(taskDescriptionField));
        wait.until(ExpectedConditions.elementToBeClickable(descriptionField)).sendKeys(taskDescription);

        WebElement statusField = wait.until(ExpectedConditions.visibilityOfElementLocated(taskStatusField));
        wait.until(ExpectedConditions.elementToBeClickable(statusField)).sendKeys(taskStatus);

        // Select task status from dropdown
        driver.findElement(taskStatusField).click();

        By optionLocator = By.xpath("//ul[@class='dropdown-content select-dropdown']/li/span[text()='"+taskStatus+"']");
        WebElement optionElement = driver.findElement(optionLocator);
        optionElement.click();

        // Enter task label and select from autocomplete suggestions
        WebElement labelField = driver.findElement(taskLabelField);
        labelField.sendKeys(taskLabel);

        // Wait for suggestions to appear and select the first one

        labelField.sendKeys(Keys.ARROW_DOWN);
        driver.findElement(By.xpath("//li[text()='"+taskLabel+"']")).click();

        driver.findElement(taskNameField).click();
        driver.findElement(saveTaskButton).click();
    }


    // Method to view a task
    public void viewTask(String taskName) {
        WebElement task = driver.findElement(By.xpath("//span[contains(text(), '"+taskName+"')]/../../div/a[text()='Edit']"));
        task.click(); // Click to view task details
    }

    // Method to update a task
    public void updateTask(String newTaskName, String newTaskDescription) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(saveTaskButton)).click();
        WebElement nameField = driver.findElement(taskNameField);
        Thread.sleep(1000);
        nameField.clear();
        Thread.sleep(1000);
        nameField.sendKeys(newTaskName);
        WebElement descriptionField = driver.findElement(taskDescriptionField);
        Thread.sleep(1000);
        descriptionField.clear();
        descriptionField.sendKeys(newTaskDescription);
        driver.findElement(saveTaskButton).click();


    }

    // Method to delete a task
    public void deleteTask(String taskSummary) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(deleteTaskButton)).click();
            WebElement deleteTask = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + taskSummary + "']/ancestor::div//a[@id='btn_delete_task']")));
            deleteTask.click();

            // Handle the alert if present
            handleAlert();

            // Optionally wait for the alert to disappear after confirmation
            wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        }     catch(UnhandledAlertException uae){
                // Handle UnhandledAlertException explicitly
                System.out.println("UnhandledAlertException occurred. Handling the alert...");
                WebElement deleteTask = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + taskSummary + "']/ancestor::div//a[@id='btn_delete_task']")));
                deleteTask.click();
                handleAlert();
            } catch(Exception e){
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
    public void openTaskDB() {
        wait.until(ExpectedConditions.elementToBeClickable(taskDBLink)).click();
    }
}

