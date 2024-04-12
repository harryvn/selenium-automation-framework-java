/*
 * Description: This interface represents the configuration properties using the Owner library.
 */

package com.example.configManager;

import com.example.enums.BrowserType;
import com.example.enums.EnvironmentType;
import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties"
})

public interface FMConfig extends Config {

    /**
     * Gets the configured browser type.
     *
     * @return BrowserType representing the configured browser.
     */
    @DefaultValue("CHROME")
    @Key("browser")
    BrowserType getBrowser();

    /**
     * Gets the configured URL.
     *
     * @return String representing the configured URL.
     */
    @DefaultValue("https://the-internet.herokuapp.com")
    @Key("url")
    String getUrl();


    /**
     * Gets the configured remote Selenium Grid URL.
     *
     * @return String representing the configured remote Selenium Grid URL.
     */
    @DefaultValue("http://localhost:4444")
    @Key("remoteSeleniumGridUrl")
    String getRemoteSeleniumGridUrl();

    /**
     * Gets the configured environment mode.
     *
     * @return EnvironmentType representing the configured environment mode.
     */
    @DefaultValue("LOCAL")
    @Key("env")
    EnvironmentType getEnvMode();

    /**
     * Gets the configured username.
     *
     * @return String representing the configured username.
     */
    @DefaultValue("tomsmith")
    @Key("username")
    String getUsername();

    /**
     * Gets the configured password.
     *
     * @return String representing the configured password.
     */
    @DefaultValue("SuperSecretPassword!")
    @Key("password")
    String getPassword();

    /**
     * Get the value of the headless property, defaulting to false if not set.
     *
     * @return true if headless is set to true, false otherwise
     */
    @DefaultValue("false")
    @Key("headless")
    boolean isHeadless();

    /**
     * Get the status of whether video recording is enabled.
     *
     * @return true if video recording is enabled, false otherwise
     */
    @DefaultValue("false")
    @Key("recordVideo")
    boolean canRecordVideo();

}
