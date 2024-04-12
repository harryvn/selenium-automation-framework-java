/*
 * Description: This class provides a method to initialize a local Edge WebDriver instance.
 */

package com.example.driverManager;

import com.aventstack.extentreports.Status;
import com.example.browserCapabilities.EdgeCapabilities;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LocalEdgeDriverManager {

    /**
     * Initializes a local Edge WebDriver instance with specified options.
     *
     * @return WebDriver instance for local Edge.
     */
    public static WebDriver getLocalEdgeDriver() {
        try {
            // Create and return a new EdgeDriver instance
            return new EdgeDriver(EdgeCapabilities.getEdgeOptions());
        } catch (Exception e) {
            // Log and report any exception that occurs during initialization
            LoggerManager.error("Exception while initializing local Edge WebDriver: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to initialize local Edge WebDriver: " + e.getMessage());
            return null;
        }
    }


}
