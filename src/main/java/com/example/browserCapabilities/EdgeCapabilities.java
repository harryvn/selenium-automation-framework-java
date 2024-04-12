package com.example.browserCapabilities;

import com.example.utilities.StarterKit;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeCapabilities extends StarterKit {

    /**
     * Returns a new EdgeOptions instance with configured options.
     *
     * @return EdgeOptions with configured browser options
     */
    public static EdgeOptions getEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("start-maximized");
        edgeOptions.addArguments("--remote-allow-origins=*");
        edgeOptions.addArguments("--disable-notifications");
        edgeOptions.addArguments("--disable-popup-blocking");
        edgeOptions.addArguments("--disable-gpu");
        edgeOptions.addArguments("--no-sandbox");
        edgeOptions.addArguments("--disable-dev-shm-usage");
        edgeOptions.setCapability("se:recordVideo", true);

        if (isRecordVideo()) {
            edgeOptions.setCapability("se:recordVideo", true);
        }

        if (isHeadless()) {
            edgeOptions.addArguments("--headless");
        }

        return edgeOptions;
    }
}
