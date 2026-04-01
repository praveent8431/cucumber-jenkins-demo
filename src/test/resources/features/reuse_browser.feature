Feature: Reusing Browser with Excel Data

  Scenario: Login using Excel data
    Given user opens the login page
    When user logs in using excel "testdata/LoginData.xlsx" sheet "Sheet1"
