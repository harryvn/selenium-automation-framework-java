/*
 * Description: This class provides a method to initialize a local Chrome WebDriver instance.
 */

package com.example.driverManager;

import com.aventstack.extentreports.Status;
import com.example.browserCapabilities.ChromeCapabilities;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LocalChromeDriverManager {

    /**
     * Initializes a local Chrome WebDriver instance with specified options.
     *
     * @return WebDriver instance for local Chrome.
     */
    public static WebDriver getLocalChromeDriver() {
        try {
            // Create and return a new ChromeDriver instance
            return new ChromeDriver(ChromeCapabilities.getChromeOptions());
        } catch (Exception e) {
            // Log and report any exception that occurs during initialization
            LoggerManager.error("Exception while initializing local Chrome WebDriver: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to initialize local Chrome WebDriver: " + e.getMessage());
            return null;
        }
    }

}
