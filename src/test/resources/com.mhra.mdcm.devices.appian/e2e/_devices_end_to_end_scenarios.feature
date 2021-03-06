@e2e
Feature: End 2 End Scenarios to verify system is behaving correctly from a high level view

  @1836 @1929 @2222 @2327 @2278 @2260 @2193 @2191 @2190 @2289 @2290 @2273 @2324 @2311 @2328 @2263 @2278 @2292 @2258 @2197 @2833 @5753 @6887
  Scenario Outline: S1 Manufacturer account registration
#Register new manufacturer account, approve the task and check MHRA approval email received
    Given I am logged into appian as "<businessUser>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "Account request approved for"
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA@12345A"
#Log back in as newly created manufacturer account user and register a new organisation with devices
    And I go to list of manufacturers page
    And Provide indication of devices made
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType>      |
      | countryName | <countryNameNonEU> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
#Log back and verify task created for newly created manufacturer
    When I logout and log back into appian as "<businessUser>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "Request for manufacturer registration" and stored application identifier
    And I should received an email for stored manufacturer with heading "MHRA Device registrations service" and stored application identifier
    Examples:
      | businessUser | logBackInAs      | accountType  | approveReject | countryName    | countryNameNonEU |
      | businessNoor | manufacturerNoor | manufacturer | approve       | United Kingdom | Bangladesh       |


  @1836 @1929 @2222 @2327 @2278 @2260 @2193 @2289 @2290 @2273 @2324 @2191 @2190 @2311 @2263 @2328 @2278 @2292 @2258 @2197 @2216 @2833 @5753 @6887
  Scenario Outline: S2 AuthorisedRep account registration for non uk manufacturers
#Register new manufacturer account, approve the task and check MHRA approval email received
    Given I am logged into appian as "<businessUser>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "Account request approved for"
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA@12345A"
#Log back in as newly created manufacturer account user and register a new organisation with devices
    And I go to list of manufacturers page
    And Provide indication of devices made
    And I go to register a new manufacturer page
    And I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType>   |
      | countryName | <countryNameEU> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And I add another device to SELECTED manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber       |
      | customMade     | true                      |
      | notifiedBody   | NB 0086 BSI               |
      | productName    | FordHybrid                |
      | productModel   | FocusYeah                 |
    And Proceed to payment and confirm submit device details
#Log back and verify task created for newly created manufacturer
    When I logout and log back into appian as "<businessUser>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "Request for manufacturer registration" and stored application identifier
    And I should received an email for stored manufacturer with heading "MHRA Device registrations service" and stored application identifier
    Examples:
      | businessUser | logBackInAs      | accountType   | approveReject | countryName    | countryNameEU |
      | businessNoor | manufacturerNoor | authorisedRep | approve       | United Kingdom | United States |


  @1974 @1978 @4704 @1954 @1952 @1952 @1962 @6868 @6867
  Scenario Outline: S3 UK based manufacturer which is already registered and in need of CFS
    Given I am logged into appian as "manufacturerAuto" user
# Submit a new CFS manufacturer application
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer  |
      | countryName | United States |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
# Submit a new CFS order for the registered org
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer ""
    And I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs order review page
    When I submit payment for the CFS
    And I should received an email with subject heading "MHRA payment"
    Examples:
      | country | noCFS |
      | Brazil  | 15    |


  @1974 @1978 @4704 @1954 @1952 @1962 @6868 @6867
  Scenario Outline: S4 Non UK based authorised reps which is already registered and in need of CFS
    Given I am logged into appian as "authorisedRepAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
# Submit a new CFS order for the registered org
    Given I am logged into appian as "authorisedRepAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer ""
    And I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs order review page
    When I submit payment for the CFS
    And I should received an email with subject heading "MHRA payment"
    Examples:
      | country    | noCFS |
      | Bangladesh | 10    |


  @1845 @1974 @1952 @1962 @1971 @3979 @4330 @5141 @5212 @5126 @5128 @5673 @5674 @5583
  Scenario Outline: S4b Register and approve Non UK based manufacturers for CFS
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <country>     |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber       |
      | customMade     | false                     |
      | notifiedBody   | NB 0086 BSI               |
      | productName    | FordHybrid                |
      | productModel   | FocusYeah                 |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "<businessUser>" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "<status>" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
    Examples:
      | user              | businessUser | accountType   | country       | approveReject | status    |
      | authorisedRepAuto | businessAuto | authorisedRep | United States | approve       | Completed |
#      | manufacturerAuto  | businessAuto | manufacturer  | Brazil        | reject        | Completed |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149 @2257 @2325 @5753
  Scenario Outline: S5a Update already registered manufacturers by adding new devices
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I search for manufacturer with following text "<searchTerm>"
    And I view a random manufacturer with status "<status>" and stored search term
    And I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdn>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "<emailSubjectHeading>"
    Examples:
      | user              | logBackInAs  | searchTerm      | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody | emailSubjectHeading |
#      | authorisedRepAuto | businessAuto | DR_Authorised   | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  | MHRA Device registrations service                    |
      | manufacturerAuto  | businessAuto | DR_Manufacturer | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  | MHRA Device registrations service                    |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149 @2325 @5753
  Scenario Outline: S5b Update already registered manufacturers by adding and removing devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I search for manufacturer with following text "<searchTerm>"
    And I click on random manufacturer with status "Registered" and stored search term
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn1>      |
      | customMade     | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn2>      |
      | customMade             | false        |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
      | riskClassification     | class1       |
      | notifiedBody           | NB 0086 BSI  |
    And I remove the device with gmdn "<gmdn2>" code
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdn1>" and other details
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    And I should received an email for stored manufacturer with heading "<emailSubjectHeading>"
    Examples:
      | user             | logBackInAs  | searchTerm      | gmdn1                | gmdn2           | approveReject | deviceType             | emailSubjectHeading |
      | manufacturerAuto | businessAuto | DR_Manufacturer | Blood weighing scale | Autopsy measure | approve       | General Medical Device | MHRA Device registrations service                    |
#      | manufacturerAuto | businessAuto | DR_Manufacturer | Blood weighing scale | Autopsy measure | reject        | General Medical Device |MHRA Device registrations service                     |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149 @2216 @2325 @5753 @7015
  Scenario Outline: S5c Update already registered manufacturers by adding devices with products
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I search for manufacturer with following text "DR_Manufacturer"
    And I click on random manufacturer with status "Registered" and stored search term
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    When I add another device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "<emailSubjectHeading>"
    Examples:
      | user             | logBackInAs  | deviceType                | gmdnDefinition      | customMade | productName | emailSubjectHeading |
      | manufacturerAuto | businessAuto | Active Implantable Device | Desiccating chamber | true       | ford focus  | MHRA Device registrations service                    |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149 @2325 @5753 @7015
  Scenario Outline: S6a Update manufacturer for authorised rep which is already registered by adding devices
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I search for manufacturer with following text "DR_Auth"
    And I view a random manufacturer with status "<status>" and stored search term
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    When I add another device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "<emailSubjectHeading>"
    Examples:
      | user              | logBackInAs  | deviceType                | customMade | status     | gmdnDefinition       | productName |emailSubjectHeading|
      | authorisedRepAuto | businessAuto | Active Implantable Device | true       | Registered | Blood weighing scale | ford focus  | MHRA Device registrations service                  |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149 @2325 @5753
  Scenario Outline: S6b Update manufacturer for authorised rep which is already registered by adding and removing devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I search for manufacturer with following text "DR_Auth"
    And I click on random manufacturer with status "Registered" and stored search term
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn1>      |
      | customMade     | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn2>      |
      | customMade             | false        |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
      | riskClassification     | class1       |
      | notifiedBody           | NB 0086 BSI  |
    And I remove the device with gmdn "<gmdn2>" code
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Examples:
      | user              | logBackInAs  | gmdn1                | gmdn2           | approveReject | deviceType             |
      | authorisedRepAuto | businessAuto | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
#      | authorisedRepAuto | businessAuto | Blood weighing scale | Autopsy measure | reject        | General Medical Device |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149
  Scenario Outline: S6c Update manufacturer for authorised rep which is already registered by adding devices with products and removing products
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I search for manufacturer with following text "DR_Auth"
    And I click on random manufacturer with status "Registered" and stored search term
    When I add a device to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
      | listOfProductNames | Product1,Product2    |
    Then I should see option to add another device
    When I remove product name starting with "Product1"
    Then I should only see only 1 product
    Examples:
      | user              | deviceType                 | gmdnDefinition        | riskClassification | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford        | focus        | NB 0086 BSI  | true              | true       | true          |



#  @ignore
#  Scenario Outline: S7abc Search for organisations, devices and products
#    Given I am logged into appian as "<businessUser>" user
#    When I go to records page and click on "<page>"
#    And I perform a search for "<searchTerm>" in "<page>" page
#    Then I should see at least <count> matches in "<page>" page search results
#    Examples:
#      | businessUser | page                | searchTerm      | count |
#      | businessAuto | GMDN Devices        | AuthorisedRepRT | 1     |
#      | businessAuto | GMDN Devices        | ManufacturerRT  | 1     |
#      | businessAuto | Registered Products | AuthorisedRepRT | 1     |
#      | businessAuto | Registered Products | ManufacturerRT  | 1     |
#      | businessAuto | Organisations       | AuthorisedRepRT | 1     |
#      | businessAuto | Organisations       | ManufacturerRT  | 1     |
