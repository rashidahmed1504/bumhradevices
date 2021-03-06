Feature: As a customer I want to register new manufacturers WITHOUT devices
  so that I can come back and add devices later


  @regression @3761 @_sprint9 @create_new_org
  Scenario Outline: Users should be able to create and save new manufacturers WITHOUT adding devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And  I goto list of manufacturers page again
    Then I should see stored manufacturer appear in the registration in progress list
    Examples:
      | user              | accountType   | countryName |
      | manufacturerAuto  | manufacturer  | Brazil      |
      | authorisedRepAuto | authorisedRep | Bangladesh  |
