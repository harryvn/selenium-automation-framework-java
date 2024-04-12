/*
 * Description: This class provides a method to initialize a local Firefox WebDriver instance.
 */

package com.example.driverManager;

import com.aventstack.extentreports.Status;
import com.example.browserCapabilities.FirefoxCapabilities;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LocalFirefoxDriverManager {

    /**
     * Initializes a local Firefox WebDriver instance with specified options.
     *
     * @return WebDriver instance for local Firefox.
     */
    public static WebDriver getLocalFirefoxDriver() {
        try {
            // Create and return a new FirefoxDriver instance
            return new FirefoxDriver(FirefoxCapabilities.getFirefoxOptions());
        } catch (Exception e) {
            // Log and report any exception that occurs during initialization
            LoggerManager.error("Exception while initializing local Firefox WebDriver" + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to initialize local Firefox WebDriver: " + e.getMessage());
            return null;
        }
    }

}
