package com.example.pagefactory;

import com.aventstack.extentreports.Status;
import com.example.enums.LocatorType;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import com.example.utilities.StarterKit;

public class HomePage extends StarterKit {

    private static final String totalLinks = "//ul/li/a";

    public HomePage() {

    }

    /**
     * Validates the total number of links on the page.
     */
    public static void getTotalLinksOnPage() {
        LoggerManager.startTestCase("Verify total number of links on the page");
        ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "Verify total number of links on the page");
        try {
            getAllElements(LocatorType.XPATH, totalLinks);
        } catch (Exception e) {
            String errorMessage = "Exception occurred while getting all elements: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
            throw new RuntimeException("Exception occurred while getting all elements: " + e.getMessage(), e);
        }
        LoggerManager.endTestCase("Verified total number of links on the page");
        ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "Verified total number of links on the page");
    }
}
