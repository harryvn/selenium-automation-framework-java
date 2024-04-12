/*
 * Description: This enumeration represents the types of locators that can be used in the framework for locating web elements.
 */

package com.example.enums;

public enum LocatorType {
    ID,               // Locator type using HTML id attribute
    NAME,             // Locator type using HTML name attribute
    XPATH,            // Locator type using XPath expression
    CSSSELECTOR,      // Locator type using CSS selector
    LINKTEXT,         // Locator type using the visible text of a link
    PARTIALLINKTEXT,  // Locator type using a partial match of the visible text of a link
    CLASSNAME,        // Locator type using HTML class attribute
    TAGNAME           // Locator type using HTML tag name
}
