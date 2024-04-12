<div style="text-align: center;">

# Selenium WebDriver Automation Framework in Java

### A Selenium-based modular automation framework in Java for web application testing.

![License: GPL v3](https://img.shields.io/badge/License-GPL_v3-blue.svg)

[![GitHub stars](https://img.shields.io/github/stars/yourusername/your-repo.svg)](https://github.com/harryvn/selenium-automation-framework-java/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/yourusername/your-repo.svg)](https://github.com/harryvn/selenium-automation-framework-java/network)
[![GitHub issues](https://img.shields.io/github/issues/yourusername/your-repo.svg)](https://github.com/harryvn/selenium-automation-framework-java/issues)

</div>

---

![Platforms](https://img.shields.io/badge/platforms-Windows%20%7C%20Mac%20%7C%20Linux-blue) ![Browsers](https://img.shields.io/badge/browsers-Chrome%2C%20Edge%2C%20Firefox-blue)

![Java, JavaScript, and Shell Script](https://img.shields.io/badge/languages-Java%20%7C%20JavaScript%20%7C%20Shell%20Script-orange) ![Design Patterns](https://img.shields.io/badge/design_patterns-Singleton%2C_Page_Factory%2C_Builder-brightgreen) ![Selenium](https://img.shields.io/badge/library-selenium-brightgreen)

![Maven](https://img.shields.io/badge/build-maven-blue) ![Log4j](https://img.shields.io/badge/logging-Log4j-red) ![TestNG](https://img.shields.io/badge/testing-TestNG-green) ![Extent Report](https://img.shields.io/badge/report-ExtentReport-blue)

![Docker](https://img.shields.io/badge/container-Docker-blue) ![Selenium Grid](https://img.shields.io/badge/testing-Selenium_Grid-green)

---

## Table of Contents

1. [Introduction](#introduction)
2. [Key Features](#key-features)
3. [Project Structure](#project-structure)
4. [Packages and Classes](#packages-and-classes)
5. [Framework Diagram](#framework-diagram)
6. [Prerequisites](#prerequisites)
7. [Dependencies](#dependencies)
8. [Setup](#setup)
9. [Test Parameters Configuration](#test-parameters-configuration)
   - [Parameters](#parameters)
   - [Example Use Cases](#example-use-cases)
10. [Convenient Script](#convenient-script)
11. [License](#license)

---

## Introduction

This repository introduces a Selenium-based modular automation framework developed in Java for web application testing.
The framework is designed to be scalable, maintainable, and efficient, allowing you to automate tests for your web
applications.

---

## Key Features

| Feature                    | Description                                                                                                   |
|----------------------------|---------------------------------------------------------------------------------------------------------------|
| `Modular Design`           | The framework follows a modular architecture, promoting separation of concerns and improving maintainability. |
| `Page Object Model (POM)`  | It utilises the Page Object Model pattern for better code organisation and reusability.                       |
| `TestNG Integration`       | TestNG is integrated for robust test management, parallel execution, and reporting capabilities.              |
| `Logging and Reporting`    | Logging and reporting mechanisms provide detailed insights into test execution.                               |
| `Configuration Management` | External configuration files manage test environment configurations.                                          |
| `Cross-Browser Testing`    | The framework supports testing across various browsers.                                                       |
| `Parallel Execution`       | Test suites can be executed concurrently, reducing execution time.                                            |
| `Customisable`             | Framework components are designed to be customisable for specific project requirements.                       |

---

## Project Structure

The project structure is well-organised, with clear separation of functionalities. The provided image offers a visual
representation of the structure.

   ```bash
   .
   ├── logs
   │   └── automation.log
   ├── pom.xml
   ├── reports
   │   └── AutomationReport.html
   ├── runTests.sh
   ├── src
   │   ├── main
   │   │   ├── java
   │   │   │   └── com
   │   │   │       └── example
   │   │   │           ├── assertions
   │   │   │           │   └── SoftAssertionManager.java
   │   │   │           ├── browserCapabilities
   │   │   │           │   ├── ChromeCapabilities.java
   │   │   │           │   ├── EdgeCapabilities.java
   │   │   │           │   └── FirefoxCapabilities.java
   │   │   │           ├── browserManager
   │   │   │           │   ├── BrowserManager.java
   │   │   │           │   ├── LocalBrowserManager.java
   │   │   │           │   └── RemoteBrowserManager.java
   │   │   │           ├── configManager
   │   │   │           │   ├── ConfigFactory.java
   │   │   │           │   └── FMConfig.java
   │   │   │           ├── driverManager
   │   │   │           │   ├── DriverManager.java
   │   │   │           │   ├── LocalChromeDriverManager.java
   │   │   │           │   ├── LocalEdgeDriverManager.java
   │   │   │           │   ├── LocalFirefoxDriverManager.java
   │   │   │           │   ├── RemoteChromeDriverManager.java
   │   │   │           │   ├── RemoteEdgeDriverManager.java
   │   │   │           │   └── RemoteFirefoxDriverManager.java
   │   │   │           ├── enums
   │   │   │           │   ├── BrowserType.java
   │   │   │           │   ├── EnvironmentType.java
   │   │   │           │   └── LocatorType.java
   │   │   │           ├── listeners
   │   │   │           │   └── ExtentTestListener.java
   │   │   │           ├── logManager
   │   │   │           │   └── LoggerManager.java
   │   │   │           ├── pagefactory
   │   │   │           │   ├── HomePage.java
   │   │   │           │   └── LoginPage.java
   │   │   │           ├── reportManager
   │   │   │           │   └── ExtentReportManager.java
   │   │   │           ├── testbuilder
   │   │   │           │   └── TestBuilder.java
   │   │   │           └── utilities
   │   │   │               ├── CommonUtil.java
   │   │   │               ├── ExtentReportNGUtil.java
   │   │   │               └── StarterKit.java
   │   │   └── resources
   │   │       ├── local.properties
   │   │       ├── log4j2.xml
   │   │       └── remote.properties
   │   └── test
   │       └── java
   │           └── com
   │               └── example
   │                   └── testcases
   │                       ├── HomePageTest.java
   │                       └── LoginPageTest.java
   └── testng.xml
   ```

---

## Packages and Classes

Below are the packages and classes included in the automated testing framework.

These packages and classes collectively offer a robust framework for automated testing, covering various aspects such as
assertion management, browser capabilities, WebDriver management, configuration handling, test reporting, logging, and
utilities for interaction with WebDriver and ExtentReports.

| Package                           | Class                                                                 | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
|-----------------------------------|-----------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `com.example.assertions`          | `SoftAssertionManager`                                                | Utility class for managing soft assertions in TestNG tests.                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| `com.example.browserCapabilities` | `ChromeCapabilities`<br/>`EdgeCapabilities`<br/>`FirefoxCapabilities` | Provides browser capabilities for remote execution.                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| `com.example.browserManager`      | `BrowserManager`<br/>`LocalBrowserManager`<br/>`RemoteBrowserManager` | Manages the creation of WebDriver instances based on the specified environment and browser type.                                                                                                                                                                                                                                                                                                                                                                                                 |
| `com.example.configManager`       | `ConfigFactory`<br/>`FMConfig`                                        | The utility class provides a method to retrieve configuration, leveraging the Owner library interface for representing configuration properties.                                                                                                                                                                                                                                                                                                                                                 |
| `com.example.driverManager`       | `DriverManager`                                                       | The Singleton pattern manages the ThreadLocal storage of WebDriver instances, offering methods to initialize both local and remote WebDriver instances                                                                                                                                                                                                                                                                                                                                           |
| `com.example.enums`               | `BrowserType`<br/>`EnvironmentType`<br/>`LocatorType`                 | Enumeration representing the types of browsers, environments (local and remote), locators for identifying web elements that can be used in the framework.                                                                                                                                                                                                                                                                                                                                        |
| `com.example.listeners`           | `ExtentTestListener`                                                  | Listener for ExtentReports to capture test information and generate HTML reports.                                                                                                                                                                                                                                                                                                                                                                                                                |
| `com.example.logging`             | `LoggerManager`                                                       | Utility class for managing logging throughout the framework.                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| `com.example.reportManager`       | `ExtentReportManager`                                                 | This class manages the ExtentTest instances using ThreadLocal to ensure thread safety in a multi-threaded environment. It provides methods to retrieve and set the ExtentTest instance, and logs warnings or errors if necessary.                                                                                                                                                                                                                                                                |
| `com.example.testbuilder`         | `TestBuilder`                                                         | This class provides a fluent interface for building and configuring WebDriver instances for tests. It allows users to customize the driver initialization, maximize window, delete cookies, set timeouts, and navigate to a specified URL. The class uses the DriverManager and BrowserManager for driver management.                                                                                                                                                                            |
| `com.example.utilities`           | `CommonUtil`<br/>`ExtentReportNGUtil`<br/>`StarterKit`                | Provides a utility class with common methods for WebDriver interaction, WebElement handling, and validations including WebDriverWait creation, element finding, highlighting, clicking, and validation, as well as tasks like page title and text validation, refreshing, screenshot capturing, browser driver disposal, and file deletion, alongside a utility class for configuring ExtentReports for test reporting, and a StarterKit class for initializing and closing the test environment |

---

## Framework Diagram

The following architectural diagram illustrates how different components interact during test execution,
providing a comprehensive understanding of the framework's workflow.

![selenium-automation-framework-java](https://github.com/harryvn/selenium-automation-framework-java/assets/4848094/54cdd700-d31f-4565-b5a1-ab8cb8cdadac)

---

## Prerequisites

Before leveraging the selenium-automation-framework-java for automated testing, ensure the following prerequisites are
met:

- `Java Development Kit (JDK)`: Install the latest version of JDK to support Java development.
- `Apache Maven`: Ensure Maven is installed and properly configured on your system for project build management.
- `Integrated Development Environment (IDE)`: Utilise a Java IDE such as Eclipse or IntelliJ IDEA for convenient
  development and execution.
- `Git`: Install Git, a distributed version control system, to manage the source code efficiently.

---

## Dependencies

The framework relies on the following dependencies:

- `Selenium WebDriver`: A powerful web automation framework facilitating web testing across various browsers.
- `TestNG`: A robust testing framework for Java applications, offering comprehensive testing capabilities.
- `Owner`: A Java properties management library for streamlined configuration handling.
- `SLF4J API, SLF4J Simple`: Logging facade and simple implementation for logging in Java.
- `Log4j Core, Log4j API`: Logging library and API for Java, enhancing logging capabilities.
- `Extent Reports`: A reporting library for test automation, providing detailed and visually appealing test reports.
- `Commons IO`: Utility classes for performing I/O operations in Java.

---

## Setup

Setting up `selenium-automation-framework-java` is straightforward:

1. **Clone the Repository**: Clone the repository to your local machine using the provided Git clone command:
   ```bash
   git clone https://github.com/harryvn/selenium-automation-framework-java.git
   ```

2. **Execute the command**: Run the provided Maven command to execute the test suite seamlessly.:
   ```bash
   mvn clean test
   ```

---

# Test Parameters Configuration

The below section explains how to configure various test parameters through a configuration file. The section includes
details on the parameters, their descriptions, and example use cases demonstrating how to run tests with different
configurations.

   ```bash
   ### SAMPLE ###
   # Configuration file for setting up test parameters
   browser=CHROME
   url=https://the-internet.herokuapp.com
   headless=false
   recordVideo=false
   remoteSeleniumGridUrl=http://localhost:4444
   env=REMOTE
   username=tomsmith
   password=SuperSecretPassword!
   ```

## Parameters

| Parameter               | Description                                                                                                 |
|-------------------------|-------------------------------------------------------------------------------------------------------------|
| `browser`               | Specifies the web browser to use for testing (e.g., `CHROME`, `EDGE`, `FIREFOX`).                           |
| `url`                   | Defines the `URL` of the application under test, enabling seamless navigation to specific web pages.        |
| `headless`              | Determines whether to run the browser in headless mode, useful for executing tests without GUI interaction. |
| `recordVideo`           | Indicates whether to record video of the test execution, facilitating post-execution analysis and review.   |
| `remoteSeleniumGridUrl` | Specifies the `URL` of the remote Selenium Grid server for distributed testing.                             |
| `env`                   | Specifies the testing environment (e.g., `LOCAL`, `REMOTE`) to target during test execution.                |
| `username`              | Provides `username` for authentication.                                                                     |
| `password`              | Provides `password` for authentication.                                                                     |
| `parallel`              | Determines whether to run tests in parallel, optimising test suite execution time.                          |

`Note: Users can customise these parameters based on their testing requirements, adjusting values as necessary to tailor the testing experience.`

## Example Use Cases

1. **Executing Tests without Any Additional Parameters:**
      ```
      mvn clean test
      ```
    - Tests will run with default configuration as no additional parameters are specified.

---

2. **Local Testing with Chrome Browser:**
      ```
      mvn clean test -Denv=LOCAL -Dbrowser=CHROME
      ```
    - Tests will run locally with Chrome browser and default value will be used for other parameters.

---

3. **Remote Testing with Chrome Browser and Video Recording:**
      ```
      mvn clean test -Denv=REMOTE -Dbrowser=CHROME -DremoteSeleniumGridUrl=<ip-address:port> -DrecordVideo=true
      ```
    - Tests will run on remote Selenium Grid with Chrome browser with video recording enabled.

---

4. **Remote Testing with Chrome Browser in Headless Mode:**
      ```
      mvn clean test -Denv=REMOTE -Dbrowser=CHROME -DremoteSeleniumGridUrl=<ip-address:port> -Dheadless=true
      ```
    - Tests will run on remote Selenium Grid with Chrome browser in headless mode.

---

5. **Authentication Testing with Username and Password:**
      ```
      mvn clean test -Denv=REMOTE -Dusername=myusername -Dpassword=mypassword
      ```
    - Tests will run on remote Selenium Grid with provided username and password for authentication.

---

6. **Running Tests in Parallel:**
      ```
      mvn clean test -Dparallel=tests
      ```
    - Tests will run in parallel reducing overall execution time.

---

7. **Specifying Application URL for Testing:**
      ```
      mvn clean test -Durl=<url-of-site-under-test>
      ```
    - Tests will run using the specified URL of the application under test, enabling testing across various
      environments (e.g., staging, production) and seamless integration with continuous integration (CI) pipelines
      through dynamic URL specification.

---

## Convenient Script

To further streamline the testing process, the repository includes a shell script named `runTests.sh`. This script
automates the execution of tests based on user inputs, offering options for browser selection, local or remote testing,
Selenium Grid configuration, video recording, parallel execution, and headless mode.

### Usage

- Ensure the script has executable permissions:
   ```bash
   chmod +x runTests.sh
   ```

- Users can simply run the `runTests.sh` script and follow the prompts to configure and execute test scenarios
  effortlessly.
   ```bash
   ./runTests.sh
   ```

`Note: The script prompts the user to select various options and constructs the appropriate Maven command to execute the Selenium tests.`

Below is an example usage of the script:

`Note: Video recording is not supported in headless mode.`

![runTests](https://github.com/harryvn/selenium-automation-framework-java/assets/4848094/62a80c39-335a-4b63-abfe-7b1b678213d5)

`Note: For detailed explanations of each option and additional information, refer to the comments within the script itself.`

---

## License

This project is licensed under the GPL License. See
the [LICENSE](https://github.com/harryvn/selenium-automation-framework-java/blob/main/LICENSE) file for details.

---

## References

[Selenium](https://www.selenium.dev/)

[TestNG](https://testng.org/)

[ExtentReports](https://www.extentreports.com/documentation/)

[Maven](https://maven.apache.org/guides/index.html)

[Log4J](https://logging.apache.org/log4j/2.x/)