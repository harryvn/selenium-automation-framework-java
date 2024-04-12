/*
 * Description: This utility class provides a method to retrieve the configuration using Owner library.
 */

package com.example.configManager;

import com.aventstack.extentreports.Status;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.aeonbits.owner.ConfigCache;

import java.util.MissingResourceException;

public final class ConfigFactory {

    /**
     * Retrieves the configuration using the Owner library.
     *
     * @return FMConfig instance representing the configuration.
     * @throws MissingResourceException if the configuration is missing.
     */
    public static FMConfig getConfig() {

        try {
            // Get or create the configuration using Owner library
            return ConfigCache.getOrCreate(FMConfig.class, System.getenv(), System.getProperties());
        } catch (MissingResourceException e) {
            // Configuration exception
            LoggerManager.error("Configuration exception: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Configuration exception: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // General exception
            LoggerManager.error("Exception: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception: " + e.getMessage());
            return null;
        }
    }
}
