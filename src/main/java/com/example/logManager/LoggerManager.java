/*
 * Description: This class provides a centralized logging mechanism using Log4j for the framework.
 */

package com.example.logManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {

    // Logger instance using Log4j
    private static final Logger log = LogManager.getLogger();

    // Get the current logger instance
    public static Logger getCurrentLog() {
        return log;
    }

    // Get information about the method call (class name, method name, and line number)
    private static String getCallInfo() {
        StackTraceElement callStack = Thread.currentThread().getStackTrace()[3];
        String className = callStack.getClassName();
        String methodName = callStack.getMethodName();
        int lineNumber = callStack.getLineNumber();

        return className + " => " + methodName + " => " + lineNumber + " ==> ";
    }

    // Log information message
    public static void info(Object message) {
        getCurrentLog().info(getCallInfo() + (message != null ? message : "null"));
    }

    // Log the start of a test case
    public static void startTestCase(String testCase) {
        log.info("\n\n==============================================================\nExecution Started : " + (testCase != null ? testCase : "null") + "\n==============================================================");
    }

    // Log the end of a test case
    public static void endTestCase(String testCase) {
        log.info("\n==============================================================\nExecution Ended : " + (testCase != null ? testCase : "null") + "\n==============================================================\n");
    }

    // Log warning message
    public static void warn(Object message) {
        getCurrentLog().warn(getCallInfo() + (message != null ? message : "null"));
    }

    // Log error message
    public static void error(Object message) {
        getCurrentLog().error(getCallInfo() + (message != null ? message : "null"));
    }

    // Log fatal message
    public static void fatal(Object message) {
        getCurrentLog().fatal(getCallInfo() + (message != null ? message : "null"));
    }

}
