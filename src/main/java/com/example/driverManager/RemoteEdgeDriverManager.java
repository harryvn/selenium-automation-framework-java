/*
 * Description: This class provides a method to initialize a remote Edge WebDriver instance.
 */

package com.example.driverManager;

import com.aventstack.extentreports.Status;
import com.example.browserCapabilities.EdgeCapabilities;
import com.example.configManager.ConfigFactory;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RemoteEdgeDriverManager {

    /**
     * Initializes a remote Edge WebDriver instance with specified options.
     *
     * @return RemoteWebDriver instance for remote Edge.
     */
    public static RemoteWebDriver getRemoteEdgeDriver() {
        try {
            // Create and return a new RemoteWebDriver instance for remote Edge
            return new RemoteWebDriver(new URL(ConfigFactory.getConfig().getRemoteSeleniumGridUrl()), EdgeCapabilities.getEdgeOptions());
        } catch (Exception e) {
            // Log and report any exception that occurs during initialization
            LoggerManager.error("Exception while initializing remote Edge WebDriver" + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to initialize remote Edge WebDriver: " + e.getMessage());
            return null;
        }
    }
}
