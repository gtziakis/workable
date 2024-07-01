package com.workable.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class BaseTest {
    protected WebDriver driver;
    private Logger logger;

    @BeforeMethod
    public void setUp() {
        // Configure ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        // Set the path to the WebDriver executable
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.get("https://pm-tool-e63fa77e3353.herokuapp.com");

        // Initialize logger
        logger = LoggerFactory.getLogger(getClass());
        logger.info("WebDriver initialized.");

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver shutdown.");
        }
    }

}


