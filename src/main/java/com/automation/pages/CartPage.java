package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * CartPage - Page Object for the Shopping Cart page.
 */
public class CartPage {

    private final Page page;

    private final Locator checkoutButton;
    private final Locator cartItemNames;
    private final Locator pageTitle;

    public CartPage(Page page) {
        this.page = page;
        this.checkoutButton = page.locator("#checkout");
        this.cartItemNames = page.locator(".inventory_item_name");
        this.pageTitle = page.locator(".title");
    }

    /**
     * Returns the title of the cart page ("Your Cart").
     */
    public String getPageTitle() {
        return pageTitle.textContent();
    }

    /**
     * Clicks the Checkout button to proceed to checkout.
     */
    public void clickCheckout() {
        checkoutButton.click();
    }

    /**
     * Returns the name of the first item present in the cart.
     * Useful for verification.
     */
    public String getFirstItemName() {
        return cartItemNames.first().textContent();
    }

    /**
     * Returns total number of items currently in the cart.
     */
    public int getItemCount() {
        return cartItemNames.count();
    }
}
