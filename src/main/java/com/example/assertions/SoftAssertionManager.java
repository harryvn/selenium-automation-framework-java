package com.example.assertions;

import com.aventstack.extentreports.Status;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.testng.asserts.SoftAssert;

public class SoftAssertionManager {

    public SoftAssert softAssert = new SoftAssert();

    /**
     * Compares the actual value with the expected value using a custom message.
     *
     * @param actualValue   the actual value to be compared
     * @param expectedValue the expected value for comparison
     * @param message       the custom message to be displayed on failure
     */

    public void assertEquals(String actualValue, String expectedValue, String message) {
        if (actualValue == null || expectedValue == null) {
            throw new IllegalArgumentException("Actual value and expected value must not be null");
        }
        softAssert.assertEquals(actualValue, expectedValue, message);
    }

    /**
     * Asserts that a condition is true.
     *
     * @param condition boolean condition to be checked
     * @param message   description of the assertion
     */

    public void assertTrue(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
    }

    /**
     * Asserts that a condition is false. If it isn't, an AssertionError, with the given message, is thrown.
     *
     * @param condition the condition to be checked
     * @param message   the error message to be displayed if the condition is true
     */
    public void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    /**
     * Asserts all the soft assertions in the SoftAssert object, and handles any exceptions that may occur.
     */
    public void assertAll() {
        try {
            softAssert.assertAll();
        } catch (Exception e) {
            LoggerManager.error("An error occurred during assertion: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "An error occurred during assertion: " + e.getMessage());
            throw e;
        }
    }

    // Add more specific verification methods as needed, e.g., for WebElement properties
}


