package com.example.browserCapabilities;

import com.example.utilities.StarterKit;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxCapabilities extends StarterKit {

    /**
     * Get Firefox options for WebDriver setup.
     *
     * @return FirefoxOptions object with configured options
     */
    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("start-maximized");
        firefoxOptions.setCapability("se:recordVideo", true);
        firefoxOptions.setCapability("webSocketUrl", true);

        if (isRecordVideo()) {
            firefoxOptions.setCapability("se:recordVideo", true);
        }

        if (isHeadless()) {
            firefoxOptions.addArguments("--headless");
        }

        return firefoxOptions;
    }
}
