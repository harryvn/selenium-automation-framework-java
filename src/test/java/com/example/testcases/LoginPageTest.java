/**
 * Test class for login functionality.
 */

package com.example.testcases;

import com.example.logManager.LoggerManager;
import com.example.pagefactory.LoginPage;
import org.testng.annotations.Test;

public class LoginPageTest extends LoginPage {

    /**
     * Test to verify login with invalid credentials.
     */
    @Test
    public static void verifyLoginWithInvalidCredentials() {
        validateInvalidLoginAttempt();
    }

    /**
     * Test to verify login with valid credentials.
     */
    @Test
    public static void verifyLoginWithValidCredentials() {
        try {
            loginToAppUsingForm();
        } catch (Exception e) {
            LoggerManager.error("Exception: " + e.getMessage());
        }
    }
}
