package com.automation.utils;

import com.microsoft.playwright.*;

/**
 * BrowserFactory - Responsible for creating and managing Playwright browser instances.
 * Centralizes browser launch logic so tests remain clean.
 */
public class BrowserFactory {

    // Playwright instance (entry point to the library)
    private static Playwright playwright;

    // Browser instance (Chromium, Firefox, or WebKit)
    private static Browser browser;

    // BrowserContext - isolated session (like incognito profile)
    private static BrowserContext context;

    // Page - represents a single browser tab
    private static Page page;

    /**
     * Initializes Playwright, launches the browser, creates a context and a page.
     * Reads browser type and headless mode from config.properties.
     */
    public static void initBrowser() {
        // Create Playwright instance
        playwright = Playwright.create();

        // Read browser name from config (chromium / firefox / webkit)
        String browserName = ConfigReader.getProperty("browser").toLowerCase();

        // Read headless mode (true = no UI, false = visible browser)
        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

        // Launch options builder
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(50); // slight delay for better visibility during debugging

        // Launch the requested browser
        switch (browserName) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(launchOptions);
                break;
        }

        // Create a new browser context (isolated cookies, storage, etc.)
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)   // set window size
                .setIgnoreHTTPSErrors(true));  // useful for test environments

        // Create a new page (tab) inside the context
        page = context.newPage();

        // Set default timeout for all actions (in milliseconds)
        page.setDefaultTimeout(Integer.parseInt(ConfigReader.getProperty("timeout")));
    }

    /**
     * Returns the current Page object so Page Objects and tests can interact with the browser.
     */
    public static Page getPage() {
        return page;
    }

    /**
     * Returns the BrowserContext (useful if you need multiple pages later).
     */
    public static BrowserContext getContext() {
        return context;
    }

    /**
     * Closes page, context, browser and Playwright instance.
     * Must be called after every test to avoid resource leaks.
     */
    public static void closeBrowser() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
