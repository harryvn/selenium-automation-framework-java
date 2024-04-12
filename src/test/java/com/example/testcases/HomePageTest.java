package com.example.testcases;

import com.example.listeners.ExtentTestListener;
import com.example.pagefactory.HomePage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestListener.class)
public class HomePageTest extends HomePage {

    /**
     * Test to validate total number of links on the page.
     */
    @Test
    public static void validateTotalLinksOnPage() {
        getTotalLinksOnPage();
    }
}
