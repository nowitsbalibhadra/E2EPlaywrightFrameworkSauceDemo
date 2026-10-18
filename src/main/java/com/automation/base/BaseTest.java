package com.automation.base;

import com.automation.utils.BrowserFactory;
import com.automation.utils.ConfigReader;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BaseTest - Parent class for all test classes.
 * Handles browser setup, teardown, tracing and screenshots.
 */
public class BaseTest {

    /**
     * Runs before every @Test method.
     * Initializes the browser, starts tracing and navigates to the base URL.
     */
    @BeforeMethod
    public void setUp() {
        // Launch browser (headed mode so you can see it)
        BrowserFactory.initBrowser();

        // Start Playwright Tracing (records everything that happens)
        BrowserFactory.getContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)      // capture screenshots in trace
                .setSnapshots(true)        // capture DOM snapshots
                .setSources(true));        // include source code

        // Navigate to the application under test
        String baseUrl = ConfigReader.getProperty("baseUrl");
        BrowserFactory.getPage().navigate(baseUrl);
    }

    /**
     * Runs after every @Test method.
     * Takes screenshot, stops tracing and closes the browser.
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        Page page = BrowserFactory.getPage();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String testName = result.getMethod().getMethodName();

        // ========== Take Screenshot ==========
        try {
            String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true));
            System.out.println("Screenshot saved: " + screenshotPath);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }

        // ========== Stop Tracing ==========
        try {
            String tracePath = "traces/" + testName + "_" + timestamp + ".zip";
            BrowserFactory.getContext().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(tracePath)));
            System.out.println("Trace saved: " + tracePath);
        } catch (Exception e) {
            System.out.println("Failed to save trace: " + e.getMessage());
        }

        // Close browser
        BrowserFactory.closeBrowser();
    }
}
