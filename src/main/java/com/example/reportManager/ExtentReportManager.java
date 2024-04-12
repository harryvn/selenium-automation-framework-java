/*
 * Description: This class manages the ExtentTest instances using ThreadLocal to ensure thread safety in a multi-threaded environment.
 *              It provides methods to retrieve and set the ExtentTest instance, and logs warnings or errors if necessary.
 */

package com.example.reportManager;

import com.aventstack.extentreports.ExtentTest;
import com.example.logManager.LoggerManager;

public class ExtentReportManager {

    // ThreadLocal variable to store ExtentTest instance for each thread
    private static final ThreadLocal<ExtentTest> extent = new ThreadLocal<>();

    // Singleton instance of ExtentReportManager
    private static final ExtentReportManager instance = new ExtentReportManager();

    // Private constructor to enforce Singleton pattern
    private ExtentReportManager() {

    }

    // Method to get the singleton instance of ExtentReportManager
    public static ExtentReportManager getReportInstance() {
        return instance;
    }

    // Method to get the ExtentTest instance for the current thread
    public ExtentTest getExtent() {

        // Retrieve ExtentTest instance from the ThreadLocal variable
        ExtentTest extentTest = extent.get();

        // Log a warning if ExtentTest instance is null
        if (extentTest == null) {
            LoggerManager.warn("ExtentTest is null. Did you forget to set it?");
        }
        return extentTest;
    }

    // Method to set the ExtentTest instance for the current thread
    public void setExtent(ExtentTest extentTest) {
        try {
            extent.set(extentTest);
        } catch (Exception e) {
            LoggerManager.error("Exception setting ExtentTest: " + e.getMessage());
        }
    }

}
