Feature: As a business user, I want a task to be created when updating or creating new MANUFACTURERS
  So that I know an action to review the request is required by myself or another team member


  @regression @mdcm-15 @2324 @mdcm-21 @2323 @mdcm-39 @2312 @mdcm-186 @2258 @mdcm-240 @2216 @_sprint4 @_sprint2 @_sprint3 @_sprint5 @2910 @_sprint7 @create_new_org @bug
  Scenario Outline: Business users should be able to review and process manufacturer and device registration tasks
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs  | accountType   | countryName | deviceType                | gmdnDefinition      | customMade | listOfProductNames |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | Active Implantable Device | Desiccating chamber | true       | setmeup1           |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Device | Desiccating chamber | true       | setmeup2           |


  @regression @mdcm-15 @2324 @mdcm-21 @2323 @mdcm-39 @2312 @mdcm-186 @2258 @_sprint2 @_sprint3 @_sprint5 @3755 @_sprint11 @2910 @_sprint7 @2049 @_sprint8 @3207 @5349 @_sprint21 @create_new_org
  Scenario Outline: Business users to review and process manufacturer and device registration task for IVD List A
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
      | listOfProductNames | <listOfProductNames> |
    And The gmdn code or term is "displayed" in summary section
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs  | accountType   | countryName | deviceType                 | gmdnDefinition        | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford,hyundai       | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford,honda         | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-161 @2276 @mdcm-232 @2221 @mdcm-164 @2274 @mdcm-240 @2216 @_sprint4 @_sprint6 @create_new_org
  Scenario Outline: Register manufacturer as authorisedRep and verify status of letter of designation is correct
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And The designation letter should be attached and the status should be "Awaiting Review"
    Examples:
      | user              | logBackInAs  | accountType   | countryName | deviceType                | gmdnDefinition       | customMade | listOfProductNames |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Device | Desiccating chamber  | true       | ford               |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Device | Blood weighing scale | false      | fiesta             |


  @regression @mdcm-263 @2197 @_sprint6 @4088 @_sprint11 @2185 @_sprint8 @2833 @_sprint14 @create_new_org @wip
  Scenario Outline: Verify only 1 task is created when we create NEW manufacturer with multiple devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
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
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    Then Task contains correct devices and products and other details for "<deviceType>"
    And Task shows devices which are arranged by device types
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAs  | accountType   | countryName | gmdn1                | gmdn2           | approveReject | deviceType             |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Blood weighing scale | Autopsy measure | approve       | General Medical Device |

