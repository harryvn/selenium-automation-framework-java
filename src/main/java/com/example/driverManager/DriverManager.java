/*
 * Description: This class manages the ThreadLocal storage of WebDriver instances using a Singleton pattern.
 */


package com.example.driverManager;

import com.aventstack.extentreports.Status;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    // ThreadLocal to store WebDriver instances for each thread
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Singleton instance of DriverManager
    private static DriverManager instance = new DriverManager();

    // Private constructor to enforce Singleton pattern
    private DriverManager() {
        // private constructor to enforce Singleton pattern
    }

    /**
     * Retrieves the singleton instance of DriverManager.
     *
     * @return DriverManager instance.
     */
    public static DriverManager getDriverInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    /**
     * Removes the WebDriver instance associated with the current thread.
     */
    public void removeDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } catch (Exception e) {
                String errorMessage = "Exception while quitting WebDriver: " + e.getMessage();
                LoggerManager.error(errorMessage);
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to quit WebDriver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }

    /**
     * Retrieves the WebDriver instance associated with the current thread.
     *
     * @return WebDriver instance or null if not present.
     */
    public WebDriver getDriver() {
        try {
            return driver.get();
        } catch (Exception e) {
            LoggerManager.error("Exception while getting WebDriver: " + e.getMessage());
            return null;
        }
    }

    /**
     * Sets the WebDriver instance for the current thread.
     *
     * @param localDriver WebDriver instance to be set.
     */
    public void setDriver(WebDriver localDriver) {
        if (localDriver != null) {
            driver.set(localDriver);
        } else {
            LoggerManager.error("Null WebDriver instance provided");
        }
    }
}
