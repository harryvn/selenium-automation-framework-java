/*
 * Description: This class provides a method to initialize a remote Chrome WebDriver instance.
 */

package com.example.driverManager;

import com.aventstack.extentreports.Status;
import com.example.browserCapabilities.ChromeCapabilities;
import com.example.configManager.ConfigFactory;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RemoteChromeDriverManager {

    /**
     * Initializes a remote Chrome WebDriver instance with specified options.
     *
     * @return RemoteWebDriver instance for remote Chrome.
     */
    public static RemoteWebDriver getRemoteChromeDriver() {
        try {
            // Create and return a new RemoteWebDriver instance for remote Chrome
            return new RemoteWebDriver(new URL(ConfigFactory.getConfig().getRemoteSeleniumGridUrl()), ChromeCapabilities.getChromeOptions());
        } catch (Exception e) {
            // Log and report any exception that occurs during initialization
            LoggerManager.error("Exception while initializing remote Chrome WebDriver " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to initialize remote Chrome WebDriver: " + e.getMessage());
            return null;
        }
    }


}
