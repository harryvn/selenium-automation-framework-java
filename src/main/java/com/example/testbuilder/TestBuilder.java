/*
 * Description: This class provides a fluent interface for building and configuring WebDriver instances for tests.
 *              It allows users to customize the driver initialization, maximize window, delete cookies, set timeouts,
 *              and navigate to a specified URL. The class uses the DriverManager and BrowserManager for driver management.
 */

package com.example.testbuilder;

import com.example.browserManager.BrowserManager;
import com.example.configManager.ConfigFactory;
import com.example.driverManager.DriverManager;
import com.example.logManager.LoggerManager;

import java.time.Duration;

public class TestBuilder {

    // Public constructor
    public TestBuilder() {
    }

    // Static method to start building a test
    public static BuildTest builder() {
        return new BuildTest();
    }

    // Nested static class representing the builder
    public static class BuildTest {

        // Method to append a URL fragment and navigate to the resulting URL
        public static void append(String appendUrl) {
            try {
                String currentUrl = DriverManager.getDriverInstance().getDriver().getCurrentUrl();
                String newUrl = currentUrl + appendUrl;
                DriverManager.getDriverInstance().getDriver().get(newUrl);
                LoggerManager.info("Appended URL Fragment: " + appendUrl);
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
        }

        // Method to initialize the WebDriver based on environment settings
        public BuildTest initializeDriver() {
            try {
                DriverManager.getDriverInstance().setDriver(BrowserManager.getEnvironment(ConfigFactory.getConfig().getEnvMode(), ConfigFactory.getConfig().getBrowser()));
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
            return this;
        }

        // Method to set whether to maximize the window
        public BuildTest setMaximizeWindow(boolean maximizeWindow) {
            try {
                if (maximizeWindow) {
                    DriverManager.getDriverInstance().getDriver().manage().window().maximize();
                }
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
            return this;
        }

        // Method to set whether to delete cookies
        public BuildTest setDeleteCookies(boolean deleteCookies) {
            try {
                if (!deleteCookies) {
                    DriverManager.getDriverInstance().getDriver().manage().deleteAllCookies();
                }
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
            return this;
        }

        // Method to set implicit wait time
        public BuildTest setImplicitWait(int timeInSeconds) {
            try {
                DriverManager.getDriverInstance().getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
            return this;
        }

        // Method to set page load timeout
        public BuildTest setPageLoadTimeout(int timeInSeconds) {
            try {
                DriverManager.getDriverInstance().getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeInSeconds));
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
            return this;
        }

        // Method to set script timeout
        public BuildTest setScriptTimeout(int timeInSeconds) {
            try {
                DriverManager.getDriverInstance().getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(timeInSeconds));
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
            return this;
        }

        // Method to navigate to the specified URL
        public BuildTest url() {
            try {
                DriverManager.getDriverInstance().getDriver().get(ConfigFactory.getConfig().getUrl());
                LoggerManager.info("Url: " + ConfigFactory.getConfig().getUrl());
            } catch (Exception e) {
                LoggerManager.error("Exception: " + e.getMessage());
            }
            return this;
        }

        // Method to complete the building process and return the TestBuilder instance
        public TestBuilder build() {
            return new TestBuilder();
        }
    }
}
