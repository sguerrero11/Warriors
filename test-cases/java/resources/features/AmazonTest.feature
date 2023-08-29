Feature: Perform a search
  In order to test a search in the home page
  As a registered or invited user
  I want to perform a search

  ##Background:

  Scenario: Amazon Test

    Given User navigates to url "http://amazon.com"
    When User inputs text "selenium test" on "//input[@id='twotabsearchtextbox']"
    When User clicks on "//input[@type='submit']"
    When User clicks on "//span[contains(text(),'Testing Tools Cookbook')]"
    Then User verifies value of "//span[@class='a-color-base']/span" is "$29.99"
    Then User closes the browser

  ##Example:
  ## Scenario Outline:
  ## | user | password |
  ## | jeje@jeje.com | 12345 |

## you can have multiple scenarios but only 1 feature