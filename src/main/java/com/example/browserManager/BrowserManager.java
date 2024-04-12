/*
 * Description: This class manages the creation of WebDriver instances based on the specified environment and browser type.
 */

package com.example.browserManager;

import com.aventstack.extentreports.Status;
import com.example.enums.BrowserType;
import com.example.enums.EnvironmentType;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class BrowserManager {

    /**
     * Retrieves a WebDriver instance based on the specified environment and browser type.
     *
     * @param environmentType The type of environment (LOCAL or REMOTE).
     * @param browserType     The type of browser (CHROME, FIREFOX, etc.).
     * @return WebDriver instance for the specified environment and browser type.
     * @throws UnsupportedOperationException if an unsupported environment type is provided.
     * @throws WebDriverException            if a WebDriver-related exception occurs.
     * @throws RuntimeException              if a general exception occurs.
     */
    public static WebDriver getEnvironment(EnvironmentType environmentType, BrowserType browserType) {

        WebDriver driver;
        try {

            switch (environmentType) {
                case LOCAL:
                    // Get local WebDriver instance
                    driver = LocalBrowserManager.getLocalDriver(browserType);
                    break;
                case REMOTE:
                    // Get remote WebDriver instance
                    driver = RemoteBrowserManager.getRemoteDriver(browserType);
                    break;
                default:
                    // Unsupported environment type
                    String errorMessage = "Unsupported environment type: " + environmentType + " for browser type: " + browserType;
                    LoggerManager.error(errorMessage);
                    ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
                    throw new UnsupportedOperationException(errorMessage);
            }

            if (driver == null) {
                // WebDriver instance not initialized
                String errorMessage = "WebDriver instance is not initialized for the specified environment type: " + environmentType;
                LoggerManager.error(errorMessage);
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
            }

            return driver;
        } catch (WebDriverException e) {
            // WebDriver-related exception
            String errorMessage = "WebDriver exception: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
            throw new WebDriverException(errorMessage, e);
        } catch (Exception e) {
            // General exception
            String errorMessage = "Exception: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }
}
