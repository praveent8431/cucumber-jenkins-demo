package com.demo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    // ✅ For Cucumber report attachment
    public static byte[] captureScreenshotAsBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // ✅ For saving screenshot to file
    public static String captureScreenshotToFile(WebDriver driver, String scenarioName) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");

            Path screenshotDir = Paths.get("target", "screenshots");
            Files.createDirectories(screenshotDir);

            String fileName = safeName + "_" + timeStamp + ".png";
            Path destination = screenshotDir.resolve(fileName);

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), destination);
            System.out.println(source);

            return destination.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }
}