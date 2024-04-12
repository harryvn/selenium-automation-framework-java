/*
 * Description: This utility class provides common methods for interacting with the WebDriver, handling WebElement
 *              actions, and performing validations. It includes methods for creating WebDriverWait, finding elements,
 *              highlighting elements, sending keys, clicking elements, validating element presence, validating page title
 *              and text, refreshing the page, taking screenshots, disposing browser drivers, and deleting files.
 */

package com.example.utilities;

import com.aventstack.extentreports.Status;
import com.example.assertions.SoftAssertionManager;
import com.example.configManager.ConfigFactory;
import com.example.driverManager.DriverManager;
import com.example.enums.LocatorType;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    protected static final String userDirectory = System.getProperty("user.dir");
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final String SCREENSHOTS_DIRECTORY = "/reports/screenshots/";
    protected static SoftAssertionManager softAssertionManager = new SoftAssertionManager();

    /**
     * Method to create and return WebDriverWait with a default timeout
     *
     * @return WebDriverWait object
     */
    private static WebDriverWait getWebDriverWait() {
        try {
            return new WebDriverWait(
                    DriverManager.getDriverInstance().getDriver(),
                    DEFAULT_TIMEOUT
            );
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            LoggerManager.error("Exception occurred while creating WebDriverWait: " + errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception occurred while creating WebDriverWait: " + errorMessage);
            throw new RuntimeException("WebDriverWait initialization failed: " + errorMessage, e);
        }
    }

    /**
     * Method to retrieve WebElement based on the specified locator type and value
     *
     * @param locatorType  The type of the locator
     * @param locatorValue The value of the locator
     * @return The WebElement if found, otherwise null
     */
    private static WebElement getElementLocatorByType(LocatorType locatorType, String locatorValue) {
        WebElement element = null;
        try {
            // Wait for the presence of the element and retrieve it
            element = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(getBy(locatorType, locatorValue)));
            // Highlight the retrieved WebElement
            if (element != null) {
                highlightWebElement(element);
            }
        } catch (Exception e) {
            // Log the exception
            LoggerManager.error("Exception: " + e.getMessage());
            // Log the exception in the Extent Report
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception: " + e.getMessage());
        }
        return element;
    }

    /**
     * Get all elements based on the provided locator type and value.
     * Highlight each element, print its text, and display the total number of elements found.
     *
     * @param locatorType  The type of locator to use (e.g., ID, XPATH)
     * @param locatorValue The value of the locator
     */
    protected static void getAllElements(LocatorType locatorType, String locatorValue) {

        List<WebElement> elements;

        try {
            elements = getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(getBy(locatorType, locatorValue)));
            System.out.println("Verifying total number of elements present on the page...");
            int count = 1;
            String elementText = "Element ";
            for (WebElement element : elements) {
                autoScrollToElement(element);
                highlightWebElement(element);
                System.out.println(elementText + count + ": " + element.getText());
                count++;
            }
            String totalElements = "Total " + elements.size() + " elements found on the page.";
            System.out.println(totalElements);
            LoggerManager.info(totalElements);
            ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, totalElements);
        } catch (Exception e) {
            LoggerManager.error("Exception: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception: " + e.getMessage());
        }
    }

    private static void autoScrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriverInstance().getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Method to validate if an element is present and active on the page
     *
     * @param element the WebElement to be validated
     * @return true if the element is present and active, false otherwise
     */
    private static boolean validateElementIsPresent(WebElement element) {
        String elementValue = element.getText();
        String logMessage = elementValue + " is";
        try {
            if (element.isDisplayed() && element.isEnabled()) {
                logMessage += " present and active on the page.";
                LoggerManager.info(logMessage);
                ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, logMessage);
                return true;
            } else {
                logMessage += " not active on the page.";
                LoggerManager.info(logMessage);
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, logMessage);
                return false;
            }
        } catch (Exception e) {
            logMessage += " not present on the page. Error: " + e.getMessage();
            LoggerManager.error(logMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, logMessage);
            return false;
        }
    }

    /**
     * Convert LocatorType and locatorValue into By object
     *
     * @param locatorType  The type of locator (ID, NAME, XPATH, CSSSELECTOR, LINKTEXT, PARTIALLINKTEXT, CLASSNAME, TAGNAME)
     * @param locatorValue The value of the locator
     * @return By object based on the locatorType and locatorValue
     */
    private static By getBy(LocatorType locatorType, String locatorValue) {
        switch (locatorType) {
            case ID:
                return By.id(locatorValue);
            case NAME:
                return By.name(locatorValue);
            case XPATH:
                return By.xpath(locatorValue);
            case CSSSELECTOR:
                return By.cssSelector(locatorValue);
            case LINKTEXT:
                return By.linkText(locatorValue);
            case PARTIALLINKTEXT:
                return By.partialLinkText(locatorValue);
            case CLASSNAME:
                return By.className(locatorValue);
            case TAGNAME:
                return By.tagName(locatorValue);
            default:
                throw new IllegalArgumentException("Invalid locator type: " + locatorType);
        }
    }

    /**
     * Method to send keys to the specified locator type and value
     *
     * @param locatorType  The type of locator to use
     * @param locatorValue The value of the locator
     * @param sendValue    The value to send
     */
    protected static void sendKeysToLocator(LocatorType locatorType, String locatorValue, String sendValue) {
        WebElement element = getElementLocatorByType(locatorType, locatorValue);
        if (element != null) {
            try {
                element.clear();
                element.sendKeys(sendValue);
            } catch (Exception e) {
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Failed to send keys to element");
            }
        } else {
            LoggerManager.error("Element not found for locator type: " + locatorType);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Element not found for locator type: " + locatorType);
        }
    }

    /**
     * Method to click on the element located by the specified type and value
     *
     * @param locatorType  The type of locator to use (e.g., ID, XPath)
     * @param locatorValue The value of the locator
     */
    protected static void clickLocatedElement(LocatorType locatorType, String locatorValue) {
        WebElement element = getElementLocatorByType(locatorType, locatorValue);

        try {
            if (element != null && validateElementIsPresent(element)) {
                element.click();
            } else {
                LoggerManager.error("Element not found for locator type: " + locatorType);
            }
        } catch (Exception e) {
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Element not found for locator type: " + locatorType);
        }
    }

    /**
     * Method to highlight a WebElement using JavaScriptExecutor
     *
     * @param element the WebElement to be highlighted
     */
    private static void highlightWebElement(WebElement element) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriverInstance().getDriver();
            if (jse != null) {
                jse.executeScript("arguments[0].setAttribute('style', 'background-color: yellow;');", element);
                Thread.sleep(3000);
                jse.executeScript("arguments[0].setAttribute('style','border: 3px solid cyan;')", element);
            } else {
                throw new NullPointerException("JavascriptExecutor is null");
            }
        } catch (Exception e) {
            LoggerManager.error("Exception: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, e.getMessage());
        }
    }

    /**
     * Get the current page title from the driver.
     *
     * @return The current page title as a String.
     */
    private static String getPageTitle() {
        return DriverManager.getDriverInstance().getDriver().getTitle();
    }

    /**
     * Method to get the text of an element located by type and value
     *
     * @param locatorType  the type of locator
     * @param locatorValue the value of the locator
     * @return the text of the element
     */
    private static String getElementText(LocatorType locatorType, String locatorValue) {
        WebElement element = getElementLocatorByType(locatorType, locatorValue);
        return element.getText();
    }

    /**
     * Retrieves text content of a pseudo element or regular element based on the locator type and value.
     * If the locator contains '::before' or '::after', it uses JavaScript to retrieve the content,
     * otherwise, it uses WebDriver's getText method.
     *
     * @param locatorType  The type of locator (e.g., ID, CLASS_NAME)
     * @param locatorValue The value of the locator
     * @return The text content of the element or pseudo element
     */
    protected static String getPseudoElementText(LocatorType locatorType, String locatorValue) {
        WebElement element = getElementLocatorByType(locatorType, locatorValue);
        if (element == null) {
            System.out.println("Element with locatorType " + locatorType + " and locatorValue " + locatorValue + " was not found.");
            return "";
        }
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriverInstance().getDriver();
        String text;
        if (locatorValue.contains("::before")) {
            text = (String) js.executeScript("return window.getComputedStyle(document.querySelector('#" + locatorValue + "'), '::before').getPropertyValue('content')", element);
            System.out.println("Retrieved pseudo element text using JavaScript: " + text);
        } else if (locatorValue.contains("::after")) {
            text = (String) js.executeScript("return window.getComputedStyle(document.querySelector('#" + locatorValue + "'), '::after').getPropertyValue('content')", element);
            System.out.println("Retrieved pseudo element text using JavaScript: " + text);
        } else {
            text = element.getText();
            System.out.println("Retrieved element text using WebDriver's getText method: " + text);
        }
        return text.trim(); // Trim to remove any leading or trailing whitespace
    }


    /**
     * Method to validate the page title
     *
     * @param expectedPageTitle The expected title of the page
     */
    protected static void validatePageTitle(String expectedPageTitle) {
        String actualPageTitle = getPageTitle();
        try {
            if (!isValidationSuccess(actualPageTitle, expectedPageTitle)) {
                softAssertionManager.assertEquals(actualPageTitle, expectedPageTitle, "Title Validation Failed.");
                LoggerManager.error("Title Validation Failed. Expected: " + expectedPageTitle + " but got: " + actualPageTitle);
                ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Title Validation Failed. Expected: " + expectedPageTitle + " but got: " + actualPageTitle);
            } else {
                softAssertionManager.assertEquals(actualPageTitle, expectedPageTitle, "Title Validation Passed.");
                LoggerManager.info("Text Validation Passed. Expected: " + expectedPageTitle + " and got: " + actualPageTitle);
                ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "Title Validation Passed. Expected: " + expectedPageTitle + " and got: " + actualPageTitle);
            }
        } catch (Exception e) {
            LoggerManager.error("Exception occurred during title validation: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception occurred during title validation: " + e.getMessage());
        }
    }

    /**
     * Method to validate the text of an element
     *
     * @param locatorType      The type of locator used to find the element
     * @param locatorValue     The value of the locator used to find the element
     * @param expectedPageText The expected text of the element
     */
    protected static void validatePageText(LocatorType locatorType, String locatorValue, String expectedPageText) {
        String actualPageText = getElementText(locatorType, locatorValue);
        if (!isValidationSuccess(actualPageText, expectedPageText)) {
            softAssertionManager.assertEquals(actualPageText, expectedPageText, "Text Validation Failed.");
            LoggerManager.error("Text Validation Failed. Expected: " + expectedPageText + " but got: " + actualPageText);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Text Validation Failed. Expected: " + expectedPageText + " but got: " + actualPageText);
        } else {
            LoggerManager.info("Text Validation Passed. Expected: " + expectedPageText + " and got: " + actualPageText);
            ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "Text Validation Passed. Expected: " + expectedPageText + " and got: " + actualPageText);
        }
    }

    /**
     * Checks if the actual value matches the expected value.
     *
     * @param actualValue   The actual value to be validated.
     * @param expectedValue The expected value to compare against.
     * @return True if the actual value matches the expected value, false otherwise.
     */
    private static boolean isValidationSuccess(String actualValue, String expectedValue) {
        if (actualValue == null || expectedValue == null) {
            throw new IllegalArgumentException("Input values cannot be null");
        }
        return actualValue.equals(expectedValue);
    }

    /**
     * Method to refresh the current page
     */
    protected static void refreshCurrentPage() {
        DriverManager.getDriverInstance().getDriver().navigate().refresh();
    }

    /**
     * A function to get the window handle using the WebDriver instance.
     *
     * @return the window handle as a String
     */
    private static String getWindowHandle() {
        WebDriver driver = DriverManager.getDriverInstance().getDriver();
        String windowHandle = driver.getWindowHandle();
        System.out.printf("Window handle: %s%n", windowHandle);
        return windowHandle;
    }

    /**
     * Returns the current date and time in the format "dd-MM-yyyy_HH-mm-ss".
     *
     * @return String representing the current date and time
     */
    private static String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Method to dispose of the WebDriver session
     */
    protected static void disposeDriver() {
        try {
            DriverManager.getDriverInstance().removeDriver();
            LoggerManager.info("Browser driver session disposed");
        } catch (Exception e) {
            LoggerManager.error("Exception: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception: " + e.getMessage());
        }
    }

    /**
     * Method to capture a screenshot and return the file path
     *
     * @param testCaseName The name of the test case
     * @return The file path of the captured screenshot
     */
    protected static String getScreenshot(String testCaseName) {
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriverInstance().getDriver();
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        String destFile = userDirectory + SCREENSHOTS_DIRECTORY + testCaseName + getDateTime() + ".jpg";

        try {
            if (srcFile != null) {
                FileUtils.copyFile(srcFile, new File(destFile));
                // Log screenshot capture success
                LoggerManager.info("Screenshot for " + testCaseName + " captured successfully");
            } else {
                // Log if srcFile is null
                LoggerManager.error("Failed to capture screenshot for " + testCaseName + ": srcFile is null");
            }
        } catch (IOException e) {
            // Log screenshot capture failure
            LoggerManager.error("Could not capture screenshot for " + testCaseName + ": " + e.getMessage());
        }
        return destFile;
    }


    /**
     * Method to delete the reports directory
     */
    protected static void deleteReportsDirectory() {
        String srcPath = userDirectory + "/reports/";
        File directory = new File(srcPath);

        if (directory.exists()) {
            try {
                FileUtils.deleteDirectory(directory);
                LoggerManager.info("Directory '" + srcPath + "' deleted successfully!");
            } catch (IOException e) {
                handleDeleteDirectoryException(srcPath, e);
            }
        } else {
            LoggerManager.info("Directory '" + srcPath + "' does not exist. No files to delete.");
        }
    }

    /**
     * Handles the exception when deleting a directory.
     *
     * @param srcPath the path of the directory that could not be deleted
     * @param e       the IOException that occurred
     */
    private static void handleDeleteDirectoryException(String srcPath, IOException e) {
        LoggerManager.error("Could not delete directory '" + srcPath + "': " + e.getMessage());
        ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Could not delete directory '" + srcPath + "': " + e.getMessage());
    }

    /**
     * Check if the application is running in headless mode.
     *
     * @return true if the application is headless, false otherwise
     */
    protected static boolean isHeadless() {
        try {
            return ConfigFactory.getConfig().isHeadless();
        } catch (Exception e) {
            LoggerManager.error("Exception: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception: " + e.getMessage());
            return false;
        }
    }

    /**
     * Overrides the parallel attribute in the TestNG XML file based on the specified parallel mode.
     * If the parallel mode is invalid, it defaults to 'none'.
     *
     * @param xmlFilePath The path to the TestNG XML file.
     */
    protected static void setParallelExecution(String xmlFilePath) {
        // Set the property to skip the directory check
        System.setProperty("org.uncommons.reportng.skip-directory-check", "true");
        // Log the value to verify if it's set correctly
        System.out.println("Skip directory check property value: " + System.getProperty("org.uncommons.reportng.skip-directory-check"));

        boolean isParallel = Boolean.parseBoolean(System.getProperty("parallel", "false"));

        TestNG testNG = new TestNG();

        List<XmlSuite> suites = new ArrayList<>();
        XmlSuite suite = new XmlSuite();
        suite.setFileName(xmlFilePath);

        if (isParallel) {
            XmlSuite.ParallelMode parallelModeEnum = getParallelMode("none");
            suite.setParallel(parallelModeEnum);
        } else {
            suite.setParallel(XmlSuite.ParallelMode.NONE);
        }

        suites.add(suite);
        testNG.setXmlSuites(suites);

        testNG.run();
    }

    /**
     * Converts a string representation of parallel mode to the corresponding XmlSuite.ParallelMode enum.
     * If the specified parallel mode is invalid, it defaults to 'none' and prints a warning message.
     *
     * @param parallelMode The string representation of the parallel mode.
     * @return The XmlSuite.ParallelMode enum corresponding to the parallel mode string.
     */
    private static XmlSuite.ParallelMode getParallelMode(String parallelMode) {
        switch (parallelMode.toLowerCase()) {
            case "tests":
                return XmlSuite.ParallelMode.TESTS;
            case "classes":
                return XmlSuite.ParallelMode.CLASSES;
            case "methods":
                return XmlSuite.ParallelMode.METHODS;
            case "instances":
                return XmlSuite.ParallelMode.INSTANCES;
            default:
                System.out.println("Invalid parallel mode specified. Defaulting to 'none'.");
                return XmlSuite.ParallelMode.NONE;
        }
    }

    /**
     * Checks if video recording is allowed.
     *
     * @return true if video recording is allowed, false otherwise.
     */
    protected static boolean isRecordVideo() {
        try {
            return ConfigFactory.getConfig().canRecordVideo();
        } catch (Exception e) {
            LoggerManager.error("Exception: " + e.getMessage());
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, "Exception: " + e.getMessage());
            return false;
        }
    }

}
