/*
 * Description: This class implements the TestNG ITestListener interface to handle and customize test execution events for ExtentReports.
 */

package com.example.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import com.example.utilities.CommonUtil;
import com.example.utilities.ExtentReportNGUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener extends CommonUtil implements ITestListener {

    // Static variable to store the ExtentReports instance
    private static ExtentReports extentReport;

    // Instance variable to store the ExtentTest instance
    ExtentTest extentTest;

    /**
     * A description of the entire Java function.
     *
     * @param testResult description of parameter
     * @return description of return value
     */
    @Override
    public void onTestStart(ITestResult testResult) {
        try {
            // Create a new ExtentTest instance for the current test case
            extentTest = extentReport.createTest(testResult.getMethod().getMethodName());

            // Set the ExtentTest instance in the ExtentReportManager for further reference
            ExtentReportManager.getReportInstance().setExtent(extentTest);

            // Log test start information in the extent report
            ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "Test Case: " + testResult.getMethod().getMethodName() + " is Started");
        } catch (Exception e) {
            // Log and report any exception that occurs during onTestStart
            LoggerManager.error("Exception during onTestStart: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, e.getMessage());
        }
    }

    /**
     * A method that is called when a test is successful.
     *
     * @param testResult The result of the test
     * @return void (no return value)
     */
    @Override
    public void onTestSuccess(ITestResult testResult) {
        try {
            // Log test success information in the extent report
            ExtentReportManager.getReportInstance()
                    .getExtent()
                    .log(Status.PASS, "Test Case: " +
                            testResult.getMethod().getMethodName() + " is Passed");
        } catch (Exception e) {
            // Log and report any exception that occurs during onTestSuccess
            LoggerManager.info("Exception during onTestSuccess: " + e.getMessage());
            ExtentReportManager.getReportInstance()
                    .getExtent()
                    .log(Status.FAIL, e.getMessage());
        }

    }

    /**
     * A method to handle the action to be taken when a test fails.
     *
     * @param testResult the result of the failed test
     * @return void, as there is no return value
     */
    @Override
    public void onTestFailure(ITestResult testResult) {
        try {
            // Log test failure information in the extent report
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Test Case: " + testResult.getMethod().getMethodName() + " is Failed");
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, testResult.getThrowable().getMessage());

            // Capture and attach a screenshot to the extent report for the failed test case
            String testMethodName = testResult.getMethod().getMethodName();
            ExtentReportManager.getReportInstance().getExtent().addScreenCaptureFromPath(getScreenshot(testMethodName), testMethodName);
        } catch (Exception e) {
            // Log and report any exception that occurs during onTestFailure
            LoggerManager.error("Exception during onTestFailure: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, e.getMessage());
        }

    }

    /**
     * A method that is called when a test is skipped.
     *
     * @param testResult The test result object containing information about the skipped test
     * @return void, as there is no return value
     */
    @Override
    public void onTestSkipped(ITestResult testResult) {
        try {
            // Create a new ExtentReports instance when a test case is skipped
            if (extentReport == null) {
                extentReport = ExtentReportNGUtil.createExtentReports();
            }

            // Log test skipped information in the extent report
            if (ExtentReportManager.getReportInstance() != null && ExtentReportManager.getReportInstance().getExtent() != null) {
                ExtentReportManager.getReportInstance().getExtent().log(Status.SKIP, "Test Case: " + testResult.getMethod().getMethodName() + " is Skipped");
            }
        } catch (Exception e) {
            // Log and report any exception that occurs during onTestSkipped
            LoggerManager.info("Exception during onTestSkipped: " + e.getMessage());
            if (ExtentReportManager.getReportInstance() != null && ExtentReportManager.getReportInstance().getExtent() != null) {
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, e.getMessage());
            }

        }
    }

    /**
     * This method is called when the test execution starts. It creates a new ExtentReports instance
     * and handles any exceptions that occur during the execution of this method.
     *
     * @param context the test context object
     */
    @Override
    public void onStart(ITestContext context) {
        try {
            // Create a new ExtentReports instance at the start of the test execution
            extentReport = ExtentReportNGUtil.createExtentReports();
        } catch (Exception e) {
            // Log and report any exception that occurs during onStart
            LoggerManager.error("Exception during onStart: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, e.getMessage());
        }
    }

    /**
     * A description of the entire Java function.
     *
     * @param context description of parameter
     * @return description of return value
     */
    @Override
    public void onFinish(ITestContext context) {
        try {
            // Flush the extent report at the end of the test execution
            if (extentReport != null) {
                extentReport.flush();
            } else {
                LoggerManager.error("ExtentReport is null");
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "ExtentReport is null");
            }
        } catch (Exception e) {
            // Log and report any exception that occurs during onFinish
            LoggerManager.error("Exception during onFinish: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, e.getMessage());
        }
    }
}
