package com.example.browserCapabilities;

import com.example.utilities.StarterKit;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeCapabilities extends StarterKit {

    /**
     * Returns ChromeOptions for remote execution.
     *
     * @return ChromeOptions for remote execution
     */
    public static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");

        if (isRecordVideo()) {
            chromeOptions.setCapability("se:recordVideo", true);
        }

        if (isHeadless()) {
            chromeOptions.addArguments("--headless");
        }

        return chromeOptions;
    }
}
