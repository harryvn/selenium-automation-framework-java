/**
 * StarterKit class for initializing and closing the test environment.
 */

package com.example.utilities;

import com.aventstack.extentreports.Status;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import com.example.testbuilder.TestBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class StarterKit extends CommonUtil {

    /**
     * Sets up the environment before the suite starts by configuring parallel execution and deleting existing report files.
     */
    @BeforeSuite
    public void setUp() {
        try {
            if (userDirectory != null) {
                setParallelExecution(userDirectory + "/testng.xml");
                deleteReportsDirectory();
            } else {
                System.out.println("User directory is null. Unable to set up environment.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during setup: " + e.getMessage());
        }
    }

    /**
     * Initializes the test environment before the test class starts.
     */
    @BeforeClass
    public void initializeTest() {
        try {
            TestBuilder testBuilder = TestBuilder.builder()
                    .initializeDriver()
                    .setMaximizeWindow(true)
                    .setDeleteCookies(true)
                    .setImplicitWait(10)
                    .setPageLoadTimeout(30)
                    .setScriptTimeout(30)
                    .url()
                    .build();
            // LoggerManager.info("TestBuilder details: " + testBuilder.toString());
        } catch (Exception e) {
            String errorMessage = "Exception during test initialization: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        }
    }

    /**
     * Closes the application and performs cleanup after the test class ends.
     */
    @AfterClass
    public void closeTest() {
        try {
            disposeDriver();
            softAssertionManager.assertAll();
        } catch (Exception e) {
            String errorMessage = "Exception during test teardown: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        }
    }

//    @AfterSuite
//    public void tearDown() {
//        SoftAssertionManager softAssertionManager = new SoftAssertionManager();
//        softAssertionManager.assertAll();
//    }
}
