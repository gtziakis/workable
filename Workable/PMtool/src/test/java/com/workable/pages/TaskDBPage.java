package com.workable.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TaskDBPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By searchTaskField = By.id("search");
    private By sortTaskField = By.id("sort_tasks");  // Assuming there's a button to sort by summary
    private By taskSummaryLocator = By.cssSelector(".card-title");


    // Constructor
    public TaskDBPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchTaskBySummary(String taskSummary) {
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTaskField));
        wait.until(ExpectedConditions.elementToBeClickable(searchField)).clear();
        searchField.sendKeys(taskSummary);
    }

    public int getTaskCountBySummary(String summary) {
        List<WebElement> tasks = driver.findElements(By.xpath("//span[@class='card-title' and text()='" + summary + "']"));
        return tasks.size();
    }

    public void sortTasksBySummary(boolean ascending) {
        WebElement sortButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sortTaskField));
        wait.until(ExpectedConditions.elementToBeClickable(sortButton)).click();
    }

    public String getFirstTaskSummary() {
        List<WebElement> tasks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(taskSummaryLocator));
        return tasks.get(0).getText();
    }

    public String getLastTaskSummary() {
        List<WebElement> tasks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(taskSummaryLocator));
        return tasks.get(tasks.size() - 1).getText();
    }
}