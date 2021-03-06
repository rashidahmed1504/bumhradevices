Feature: As a customer I want to register other types of organisations such as Distributors
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @2109 @_sprint13 @create_new_org @wip @bug
  Scenario Outline: Create new distributor account as business user and approve the tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA@12345A"
    And I go to list of manufacturers page
    Then I should see error message "<errorMessage>" in instead of list of manufacturers
    Examples:
      | user         | accountType | count | countryName | errorMessage                                                                                        |
      | businessNoor | distributor | 1     | Turkey      | Unfortunately, you are not eligible for registering the devices. Only manufacturers based in the UK |


  @regression @1996 @_sprint9 @2833 @_sprint14 @create_new_org @wip @bug
  Scenario Outline: Check new role added to create distributor account as business user and approve the tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    Then validate task is displaying correct new account details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA@12345A"
    When I go to records page and click on "<pageHeading>"
    And I perform a search for "<searchFor>" in "<pageHeading>" page
    Then I should see at least <count> matches in "<pageHeading>" page search results
    Examples:
      | user         | accountType | count | countryName | pageHeading   | searchFor   |
      | businessNoor | distributor | 1     | Turkey      | Organisations | Distributor |

