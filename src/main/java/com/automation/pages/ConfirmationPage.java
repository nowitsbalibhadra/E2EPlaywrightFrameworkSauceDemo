package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * ConfirmationPage - Page Object for the Order Confirmation (Thank You) page.
 */
public class ConfirmationPage {

    private final Page page;

    private final Locator confirmationHeader;
    private final Locator confirmationMessage;
    private final Locator backHomeButton;

    public ConfirmationPage(Page page) {
        this.page = page;
        this.confirmationHeader = page.locator(".complete-header");
        this.confirmationMessage = page.locator(".complete-text");
        this.backHomeButton = page.locator("#back-to-products");
    }

    /**
     * Returns the main confirmation header text (e.g. "Thank you for your order!").
     */
    public String getConfirmationHeader() {
        return confirmationHeader.textContent();
    }

    /**
     * Returns the detailed confirmation message.
     */
    public String getConfirmationMessage() {
        return confirmationMessage.textContent();
    }

    /**
     * Checks whether the order was successfully placed.
     */
    public boolean isOrderConfirmed() {
        return confirmationHeader.isVisible()
                && confirmationHeader.textContent().contains("Thank you");
    }

    /**
     * Clicks "Back Home" button.
     */
    public void clickBackHome() {
        backHomeButton.click();
    }
}
