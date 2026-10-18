package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * LoginPage - Page Object for SauceDemo Login page.
 * Contains all locators and actions related to login.
 */
public class LoginPage {

    // Playwright Page object (represents the browser tab)
    private final Page page;

    // Locators (declared as final for immutability and clarity)
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorMessage;

    /**
     * Constructor - receives the Page object from the test / base class.
     * Initializes all locators using Playwright's locator strategy.
     */
    public LoginPage(Page page) {
        this.page = page;

        // CSS selectors (Playwright recommends locator() over $())
        this.usernameInput = page.locator("#user-name");
        this.passwordInput = page.locator("#password");
        this.loginButton = page.locator("#login-button");
        this.errorMessage = page.locator("[data-test='error']");
    }

    /**
     * Enters username into the username field.
     */
    public void enterUsername(String username) {
        usernameInput.fill(username);
    }

    /**
     * Enters password into the password field.
     */
    public void enterPassword(String password) {
        passwordInput.fill(password);
    }

    /**
     * Clicks the Login button.
     */
    public void clickLogin() {
        loginButton.click();
    }

    /**
     * Performs complete login action (username + password + click).
     * This is a higher-level business method.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    /**
     * Returns the error message text if login fails.
     */
    public String getErrorMessage() {
        return errorMessage.textContent();
    }

    /**
     * Checks whether the login page is displayed (by presence of login button).
     */
    public boolean isLoginPageDisplayed() {
        return loginButton.isVisible();
    }
}
