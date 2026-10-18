package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * CheckoutPage - Handles both Checkout Information and Checkout Overview pages.
 */
public class CheckoutPage {

    private final Page page;

    // Checkout Information locators
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator postalCodeInput;
    private final Locator continueButton;

    // Checkout Overview locators
    private final Locator finishButton;
    private final Locator summaryTotal;
    private final Locator pageTitle;

    public CheckoutPage(Page page) {
        this.page = page;

        // Information page
        this.firstNameInput = page.locator("#first-name");
        this.lastNameInput = page.locator("#last-name");
        this.postalCodeInput = page.locator("#postal-code");
        this.continueButton = page.locator("#continue");

        // Overview page
        this.finishButton = page.locator("#finish");
        this.summaryTotal = page.locator(".summary_total_label");
        this.pageTitle = page.locator(".title");
    }

    /**
     * Fills the checkout information form.
     */
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        postalCodeInput.fill(postalCode);
    }

    /**
     * Clicks Continue button on Information page.
     */
    public void clickContinue() {
        continueButton.click();
    }

    /**
     * Clicks Finish button on Overview page to place the order.
     */
    public void clickFinish() {
        finishButton.click();
    }

    /**
     * Returns the total amount text from the overview page.
     */
    public String getTotalAmount() {
        return summaryTotal.textContent();
    }

    /**
     * Returns current page title (useful to know which checkout step we are on).
     */
    public String getPageTitle() {
        return pageTitle.textContent();
    }
}
