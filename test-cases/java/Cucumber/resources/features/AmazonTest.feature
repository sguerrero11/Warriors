## descriptive title + description of the feature or functionality
Feature: Perform a search
  In order to test a search in the home page
  As a registered or invited user
  I want to perform a search

  ##Background: optional steps that set up the initial context for scenarios
  ## if you have steps in the scenarios that appear over and over, you put them here instead

  ## scenario title
  Scenario: Amazon Test to check a book's price

    Given User navigates to url "http://amazon.com"
    When User inputs text "selenium test" on "//input[@id='twotabsearchtextbox']"
    When User clicks on "//input[@type='submit']"
    When User clicks on "//span[contains(text(),'Testing Tools Cookbook')]"
    Then User verifies value of "//span[@class='a-color-base']/span" is "$29.99"
    Then User closes the browser
    ## But User ...

  ##Example:
  ## Scenario Outline: Login with various credentials
  ## Given the user is on the login page
  ## When they enter <user> and <password>
  ## Then they should <result>
  ## | user | password | result |
  ## | jeje@jeje.com | 12345 | logged in successfully |
  ## | jaja@juju.com | 333 | login failed |

## you can have multiple scenarios but only 1 feature

## the features file contains the test data to be used
  ## you can have a scenario (a specific example of how a feature
  # should behave. it is typically written in Gherkin syntax and represents a single test case)
  # or a scenario outline (it is used when you want to run the same scenario with multiple
  # sets of data. it acts as a template, and you provide examples (test cases) with different inputs)
  # scenario is the same as example

  # Rule keyword (Gherkin v6 onwards): The purpose of the Rule keyword is to represent one
  # business rule that should be implemented. It provides additional information for
  # a feature. A Rule is used to group together several scenarios that belong to this business
  # rule. A Rule should contain one or more scenarios that illustrate the particular rule.