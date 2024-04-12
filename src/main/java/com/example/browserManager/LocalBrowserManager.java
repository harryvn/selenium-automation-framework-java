/*
 * Description: This class manages the creation of local WebDriver instances based on the specified browser type.
 */

package com.example.browserManager;

import com.aventstack.extentreports.Status;
import com.example.driverManager.LocalChromeDriverManager;
import com.example.driverManager.LocalEdgeDriverManager;
import com.example.driverManager.LocalFirefoxDriverManager;
import com.example.enums.BrowserType;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class LocalBrowserManager {

    /**
     * Retrieves a local WebDriver instance based on the specified browser type.
     *
     * @param browserType The type of browser (CHROME, FIREFOX, EDGE, etc.).
     * @return WebDriver instance for the specified browser type.
     * @throws UnsupportedOperationException if an unsupported browser type is provided.
     * @throws WebDriverException            if a WebDriver-related exception occurs.
     * @throws RuntimeException              if a general exception occurs.
     */
    public static WebDriver getLocalDriver(BrowserType browserType) {

        try {
            WebDriver driver;

            switch (browserType) {
                case CHROME:
                    // Initialize WebDriver based on the specified browser type
                    driver = LocalChromeDriverManager.getLocalChromeDriver();
                    break;
                case FIREFOX:
                    // Get local Firefox WebDriver instance
                    driver = LocalFirefoxDriverManager.getLocalFirefoxDriver();
                    break;
                case EDGE:
                    // Get local Edge WebDriver instance
                    driver = LocalEdgeDriverManager.getLocalEdgeDriver();
                    break;
                default:
                    // Unsupported browser type
                    String errorMessage = "Unsupported browser type: " + browserType;
                    LoggerManager.error(errorMessage);
                    ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
                    throw new UnsupportedOperationException(errorMessage);
            }

            if (driver == null) {
                // WebDriver instance not initialized
                String errorMessage = "WebDriver instance is not initialized for the specified browser type: " + browserType;
                LoggerManager.error(errorMessage);
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "WebDriver instance is not initialized for the specified environment type: " + browserType);
            }

            return driver;

        } catch (WebDriverException e) {
            // Handle WebDriver-related exception
            String errorMessage = "WebDriver exception: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
            throw new WebDriverException(errorMessage, e);
        } catch (Exception e) {
            // Handle general exception
            String errorMessage = "Exception: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

}
