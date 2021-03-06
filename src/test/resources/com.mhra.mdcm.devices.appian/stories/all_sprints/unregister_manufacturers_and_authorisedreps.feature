Feature: As a user, I want to be able to unregister manufacturers when they are no longer required


  @2179 @_sprint18 @6619 @_sprint24 @bug @wip
  Scenario Outline: As a business user I should be able to unregister organisations
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I perform a search for "<searchTerm>" in "<link>" page
    And I filter by "<filterBy>" for the value "<filterValue>" in "<pageHeading>" page
    And I click on a random organisation link "<searchTerm>" in "<link>" page
    Then I should see business manufacturer details page for the manufacturer
    When I unregister the manufacturer for the following reason "<unregisteredReason>"
    And I confirm "yes" to unregister the manufacturer
    Then I should see application reference number generated
    And The manufacturer should no longer be registered
    Examples:
      | user         | link          | pageHeading   | filterBy          | filterValue | searchTerm        | unregisteredReason    |
      | businessAuto | Organisations | Organisations | Registered status | Registered  | AuthorisedRepRT01 | Ceased Trading        |
      | businessAuto | Organisations | Organisations | Registered status | Registered  | ManufacturerRT01  | No Longer Represented |


  @4368 @2268 @3810 @_sprint18 @2096 @6430 @_sprint23 @4546 @_sprint24 @2085 @_sprint27 @create_new_org @wip
  Scenario Outline: As a manufacturer and authorisedRep users I should be able to unregister organisations
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | Bangladesh    |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And I add another device to SELECTED manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Sinus                     |
      | customMade     | true                      |
      | productName    | Product1                  |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "Request for manufacturer registration" and stored application identifier
    And I should received an email for stored manufacturer with heading "MHRA Device registrations service" and stored application identifier
    When I logout and log back into appian as "<user>" user
    When I go to list of manufacturers page and search and view stored manufacturer
    Then I should option to unregister the manufacturer
    When I unregister the manufacturer with the following reasons "<unregisteredReason>"
    And I confirm "Yes" to unregister the manufacturer
    Then I should see application reference number generated
    And I should received an email for stored manufacturer with heading "MHRA Device registrations service" and stored application identifier
    Examples:
      | user              | logBackInAs  | accountType   | unregisteredReason    |
      | manufacturerAuto  | businessAuto | manufacturer  | No Longer Represented |
      | authorisedRepAuto | businessAuto | authorisedRep | Ceased Trading        |


#This tests should be ignored because we need as many Registered manufacturers as possible
  @4368 @2268 @3810 @_sprint18 @2096 @6430 @_sprint23 @4546 @_sprint24 @ignore
  Scenario Outline: Unregister already registered manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I view a random manufacturer with status "Registered"
    Then I should option to unregister the manufacturer
    When I unregister the manufacturer with the following reasons "<unregisteredReason>"
    And I confirm "Yes" to unregister the manufacturer
    Then I should see application reference number generated
    And I should received an email for stored manufacturer with heading "MHRA device registration service" and stored application identifier
    Examples:
      | user              | unregisteredReason    |
      | manufacturerAuto  | No Longer Represented |
      | authorisedRepAuto | Ceased Trading        |