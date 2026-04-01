package com.demo.stepdefinitions;

import java.util.List;

import org.testng.Assert;

import com.demo.factory.DriverFactory;
import com.demo.pages.InternetAppPage;
import com.demo.utils.ExcelUtil;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReuseBrowserSteps {

    private InternetAppPage page() {
        return new InternetAppPage(DriverFactory.getDriver());
    }

    @Given("user opens the login page")
    public void user_opens_login_page() {
        page().openLoginPage();
        DriverFactory.printSessionId();
    }

    @When("user logs in using excel {string} sheet {string}")
    public void user_logs_in_using_excel(String filePath, String sheetName) {

        List<String[]> testData = ExcelUtil.readExcel(filePath, sheetName);

        for (String[] data : testData) {

            String username = data[0];
            String password = data[1];
            String expected = data[2];

            System.out.println("Logging in with: " + username);

            page().openLoginPage();
            page().login(username, password);

            if (expected.equalsIgnoreCase("SUCCESS")) {
                Assert.assertTrue(page().isLoginSuccess());
                page().logout();
            } else {
                Assert.assertFalse(page().isLoginSuccess());
            }
        }
    }
}