package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import com.automation.utils.BrowserFactory;
import com.automation.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * PurchaseFlowTest - End-to-End test covering the complete purchase journey on SauceDemo.
 *
 * Test Scenario:
 * 1. Login with valid credentials
 * 2. Verify Inventory page is displayed
 * 3. Add a product to cart
 * 4. Go to cart and verify product
 * 5. Proceed to checkout
 * 6. Fill shipping information
 * 7. Finish the order
 * 8. Verify order confirmation message
 */
public class PurchaseFlowTest extends BaseTest {

    @Test(description = "E2E: Complete purchase flow from Login to Order Confirmation")
    public void testCompletePurchaseFlow() {

        // ========== STEP 1: Login ==========
        LoginPage loginPage = new LoginPage(BrowserFactory.getPage());

        // Read credentials from config.properties
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        // Perform login
        loginPage.login(username, password);

        // ========== STEP 2: Verify Inventory Page ==========
        InventoryPage inventoryPage = new InventoryPage(BrowserFactory.getPage());

        // Assertion: Page title should be "Products"
        Assert.assertTrue(inventoryPage.isInventoryPageDisplayed(),
                "Inventory page was not displayed after login");

        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
                "Page title mismatch on Inventory page");

        // ========== STEP 3: Add Product to Cart ==========
        String productName = "Sauce Labs Backpack";
        inventoryPage.addProductToCart(productName);

        // Verify cart badge shows 1 item
        Assert.assertEquals(inventoryPage.getCartItemCount(), "1",
                "Cart badge count is incorrect after adding product");

        // ========== STEP 4: Go to Cart ==========
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(BrowserFactory.getPage());

        // Verify we are on Cart page
        Assert.assertEquals(cartPage.getPageTitle(), "Your Cart",
                "Cart page title is incorrect");

        // Verify the product name in cart
        Assert.assertEquals(cartPage.getFirstItemName(), productName,
                "Product name in cart does not match");

        // ========== STEP 5: Proceed to Checkout ==========
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(BrowserFactory.getPage());

        // Verify Checkout Information page
        Assert.assertEquals(checkoutPage.getPageTitle(), "Checkout: Your Information",
                "Checkout Information page title mismatch");

        // ========== STEP 6: Fill Checkout Information ==========
        checkoutPage.fillCheckoutInformation("Balibhadra", "Singh", "500081");
        checkoutPage.clickContinue();

        // Verify we reached Overview page
        Assert.assertEquals(checkoutPage.getPageTitle(), "Checkout: Overview",
                "Checkout Overview page title mismatch");

        // ========== STEP 7: Finish Order ==========
        checkoutPage.clickFinish();

        // ========== STEP 8: Verify Confirmation ==========
        ConfirmationPage confirmationPage = new ConfirmationPage(BrowserFactory.getPage());

        Assert.assertTrue(confirmationPage.isOrderConfirmed(),
                "Order confirmation message was not displayed");

        Assert.assertEquals(confirmationPage.getConfirmationHeader(),
                "Thank you for your order!",
                "Confirmation header text is incorrect");

        // Optional: print confirmation message for logs
        System.out.println("Order Confirmation Message: " + confirmationPage.getConfirmationMessage());
    }
}
