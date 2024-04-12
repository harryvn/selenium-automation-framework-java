package com.example.pagefactory;

import com.aventstack.extentreports.Status;
import com.example.configManager.ConfigFactory;
import com.example.enums.LocatorType;
import com.example.logManager.LoggerManager;
import com.example.reportManager.ExtentReportManager;
import com.example.testbuilder.TestBuilder;
import com.example.utilities.StarterKit;

public class LoginPage extends StarterKit {

    // Constants for expected values and element locators
    private static final String EXPECTED_WINDOW_TITLE = "The Internet";
    private static final String USERNAME_TXT = "username";
    private static final String PASSWORD_TXT = "password";
    private static final String LOGIN_BTN = "//i[@class='fa fa-2x fa-sign-in']";
    private static final String LOGOUT_BTN = "//i[@class='icon-2x icon-signout']";
    private static final String LOGIN_PAGE_TXT = "//h2[contains(text(),'Login Page')]";
    private static final String EXPECTED_LOGIN_PAGE_TXT = "Login Page";
    private static final String INVALID_USERNAME_TXT = "invalidUsername";
    private static final String INVALID_PASSWORD_TXT = "invalidPassword";
    private static final String TOP_BANNER = "flash";
    private static final String ERR_MESSAGE_TXT = "Your username is invalid!\n" +
            "×";
    private static final String EXPECTED_TXT_AFTER_LOGIN = "You logged into a secure area!\n" +
            "×";

    /**
     * Logs in to the application using the login form.
     */
    protected static void loginToAppUsingForm() {
        LoggerManager.startTestCase("Verify user is able to login to the application");
        ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "Verify user is able to login to the application");
        try {
            refreshCurrentPage();
            validatePageTitle(EXPECTED_WINDOW_TITLE);
            validatePageText(LocatorType.XPATH, LOGIN_PAGE_TXT, EXPECTED_LOGIN_PAGE_TXT);
            enterUsername();
            enterPassword();
            clickLoginButton();
            validatePageText(LocatorType.ID, TOP_BANNER, EXPECTED_TXT_AFTER_LOGIN);
            clickLogoutButton();
            refreshCurrentPage();
            validatePageText(LocatorType.XPATH, LOGIN_PAGE_TXT, EXPECTED_LOGIN_PAGE_TXT);
        } catch (Exception e) {
            String errorMessage = "Exception occurred during login: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        } finally {
            LoggerManager.endTestCase("User is able to login to the application");
            ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "User is able to login to the application");
        }
    }

    /**
     * This method validates an invalid login attempt by entering invalid credentials and clicking the login button.
     */
    public static void validateInvalidLoginAttempt() {
        LoggerManager.endTestCase("Verify user is not able to login to the application");
        ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "Verify user is not able to login to the application");
        TestBuilder.builder().append("login");
        try {
            sendKeysToLocator(LocatorType.ID, USERNAME_TXT, INVALID_USERNAME_TXT);
            sendKeysToLocator(LocatorType.ID, PASSWORD_TXT, INVALID_PASSWORD_TXT);
            String message = "Entered Username & Password: " + INVALID_USERNAME_TXT + " / " + INVALID_PASSWORD_TXT;
            LoggerManager.info(message);
            ExtentReportManager.getReportInstance().getExtent().log(Status.PASS, message);
            clickLoginButton();
            validatePageText(LocatorType.ID, TOP_BANNER, ERR_MESSAGE_TXT);
        } catch (Exception e) {
            String errorMessage = "Failed to validate invalid login attempt: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        } finally {
            LoggerManager.endTestCase("User is not able to login to the application");
            ExtentReportManager.getReportInstance().getExtent().log(Status.INFO, "User is not able to login to the application.");
        }

    }

    /**
     * This method enters the username into the username text field.
     */
    private static void enterUsername() {
        String username = ConfigFactory.getConfig().getUsername();
        try {
            sendKeysToLocator(LocatorType.ID, USERNAME_TXT, username);
            LoggerManager.info("Entered Username: " + username);
            ExtentReportManager.getReportInstance().getExtent().log(Status.PASS, "Entered Username: " + username);
        } catch (Exception e) {
            String errorMessage = "Failed to enter username: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        }
    }

    /**
     * This method enters the password into the username text field.
     */
    private static void enterPassword() {
        String password = ConfigFactory.getConfig().getPassword();
        try {
            sendKeysToLocator(LocatorType.ID, PASSWORD_TXT, password);
            LoggerManager.info("Entered Password: " + password);
            ExtentReportManager.getReportInstance().getExtent().log(Status.PASS, "Entered Password: " + password);
        } catch (Exception e) {
            String errorMessage = "Failed to enter password: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        }
    }

    /**
     * Clicks the login button and logs the action
     */
    private static void clickLoginButton() {
        try {
            clickLocatedElement(LocatorType.XPATH, LOGIN_BTN);
            LoggerManager.info("Clicked Login button");
            ExtentReportManager.getReportInstance().getExtent().log(Status.PASS, "Clicked Login button");
        } catch (Exception e) {
            String errorMessage = "Failed to click Login button: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        }
    }

    /**
     * Method to click the logout button
     */
    private static void clickLogoutButton() {
        try {
            clickLocatedElement(LocatorType.XPATH, LOGOUT_BTN);
            LoggerManager.info("Clicked Logout button");
            ExtentReportManager.getReportInstance().getExtent().log(Status.PASS, "Clicked Logout button");
        } catch (Exception e) {
            String errorMessage = "Failed to click Logout button: " + e.getMessage();
            LoggerManager.error(errorMessage);
            ExtentReportManager.getReportInstance().getExtent().log(Status.FAIL, errorMessage);
        }
    }

}
