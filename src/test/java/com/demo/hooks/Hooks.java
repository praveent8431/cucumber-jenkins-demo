package com.demo.hooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.demo.factory.DriverFactory;
import com.demo.utils.ScreenshotUtil;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks {

    private static final Logger logger =
            LogManager.getLogger(Hooks.class);

    @BeforeAll
    public static void beforeAll() {
        logger.info("=== Opening browser once for all scenarios ===");
        DriverFactory.initDriver();
    }

    // ✅ Screenshot hook (runs after EACH scenario)
    @After
    public void captureScreenshotOnFailure(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();

        if (driver != null && scenario.isFailed()) {

            logger.error("Scenario FAILED → Taking screenshot");

            byte[] screenshotBytes =
                    ScreenshotUtil.captureScreenshotAsBytes(driver);
            scenario.attach(screenshotBytes, "image/png", scenario.getName());

            String path =
                    ScreenshotUtil.captureScreenshotToFile(driver, scenario.getName());
            logger.info("Screenshot saved at: {}", path);
        }
    }

    @AfterAll
    public static void afterAll() {
        logger.info("=== Closing browser after all scenarios ===");
        DriverFactory.quitDriver();
    }
}
