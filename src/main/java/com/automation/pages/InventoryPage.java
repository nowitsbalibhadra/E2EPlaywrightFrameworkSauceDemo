package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * InventoryPage - Page Object for the Products / Inventory page after successful login.
 */
public class InventoryPage {

    private final Page page;

    // Locators
    private final Locator pageTitle;
    private final Locator shoppingCartLink;
    private final Locator cartBadge;

    public InventoryPage(Page page) {
        this.page = page;
        this.pageTitle = page.locator(".title");
        this.shoppingCartLink = page.locator(".shopping_cart_link");
        this.cartBadge = page.locator(".shopping_cart_badge");
    }

    /**
     * Returns the title text of the inventory page ("Products").
     */
    public String getPageTitle() {
        return pageTitle.textContent();
    }

    /**
     * Adds a product to cart by its name.
     * Uses dynamic locator based on product name.
     */
    public void addProductToCart(String productName) {
        // Locate the "Add to cart" button that belongs to the given product
        Locator addToCartBtn = page.locator(
                "div.inventory_item:has-text('" + productName + "') button");
        addToCartBtn.click();
    }

    /**
     * Opens the shopping cart page.
     */
    public void goToCart() {
        shoppingCartLink.click();
    }

    /**
     * Returns the number shown on the cart badge (how many items are in cart).
     */
    public String getCartItemCount() {
        if (cartBadge.isVisible()) {
            return cartBadge.textContent();
        }
        return "0";
    }

    /**
     * Checks if the Inventory page is displayed.
     */
    public boolean isInventoryPageDisplayed() {
        return pageTitle.isVisible() && pageTitle.textContent().equals("Products");
    }
}
