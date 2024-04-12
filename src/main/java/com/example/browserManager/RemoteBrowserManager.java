/*
 * Description: This class manages the creation of remote WebDriver instances based on the specified browser type.
 */

package com.example.browserManager;

import com.aventstack.extentreports.Status;
import com.example.driverManager.RemoteChromeDriverManager;
import com.example.driverManager.RemoteEdgeDriverManager;
import com.example.driverManager.RemoteFirefoxDriverManager;
import com.example.enums.BrowserType;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class RemoteBrowserManager {

    /**
     * Retrieves a remote WebDriver instance based on the specified browser type.
     *
     * @param browserType The type of browser (CHROME, FIREFOX, EDGE, etc.).
     * @return WebDriver instance for the specified browser type.
     * @throws UnsupportedOperationException if an unsupported browser type is provided.
     * @throws WebDriverException            if a WebDriver-related exception occurs.
     * @throws RuntimeException              if a general exception occurs.
     */
    public static WebDriver getRemoteDriver(BrowserType browserType) {

        try {
            WebDriver driver;

            // Switch statement to determine the type of browser and get the corresponding remote WebDriver instance
            switch (browserType) {
                case CHROME:
                    // Initialize the driver based on the specified browser type
                    driver = RemoteChromeDriverManager.getRemoteChromeDriver();
                    break;
                case FIREFOX:
                    // Get remote Firefox WebDriver instance
                    driver = RemoteFirefoxDriverManager.getRemoteFirefoxDriver();
                    break;
                case EDGE:
                    // Get remote Edge WebDriver instance
                    driver = RemoteEdgeDriverManager.getRemoteEdgeDriver();
                    break;
                default:
                    // Unsupported browser type
                    String errorMessage = "Unsupported browser type: " + browserType;
                    LoggerManager.error(errorMessage);
                    ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
                    throw new UnsupportedOperationException(errorMessage);
            }

            if (driver == null) {
                // Log error if WebDriver instance is not initialized
                String errorMessage = "WebDriver instance is not initialized for the specified browser type: " + browserType;
                LoggerManager.error(errorMessage);
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
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
