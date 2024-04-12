/*
 * Description: This class provides a method to initialize a remote Firefox WebDriver instance.
 */

package com.example.driverManager;

import com.aventstack.extentreports.Status;
import com.example.browserCapabilities.FirefoxCapabilities;
import com.example.configManager.ConfigFactory;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RemoteFirefoxDriverManager {

    /**
     * Initializes a remote Firefox WebDriver instance with specified options.
     *
     * @return RemoteWebDriver instance for remote Firefox.
     */
    public static RemoteWebDriver getRemoteFirefoxDriver() {
        try {
            // Create and return a new RemoteWebDriver instance for remote Firefox
            return new RemoteWebDriver(new URL(ConfigFactory.getConfig().getRemoteSeleniumGridUrl()), FirefoxCapabilities.getFirefoxOptions());
        } catch (Exception e) {
            // Log and report any exception that occurs during initialization
            LoggerManager.error("Exception while initializing remote Firefox WebDriver" + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to initialize remote Firefox WebDriver: " + e.getMessage());
            return null;
        }
    }
}
