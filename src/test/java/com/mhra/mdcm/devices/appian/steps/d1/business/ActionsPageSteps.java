package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.ActionsPage;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.CreateTestsData;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class ActionsPageSteps extends CommonSteps {

    @When("^I go to actions page$")
    public void i_go_to_actions_page() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        actionsPage = mainNavigationBar.clickActions();
    }


    @When("^I go to test harness page$")
    public void i_go_to_test_harness_page() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        actionsPage = mainNavigationBar.clickActions();
        createTestsData = actionsPage.gotoTestsHarnessPage();
    }


    @When("^I create a new account using test harness page$")
    public void i_create_a_new_account_using_test_harness_page() throws Throwable {
        //go to accounts page > test harness page
        actionsPage = mainNavigationBar.clickActions();
        createTestsData = actionsPage.gotoTestsHarnessPage();

        //Now create the test data using harness page
        AccountRequest ar = new AccountRequest();
        actionsPage = createTestsData.createTestOrganisation(ar);

        boolean createdSuccessfully = actionsPage.isInActionsPage();
        if(createdSuccessfully){
            log.warn("Created a new account : " + ar.organisationName);
            scenarioSession.putData(SessionKey.organisationName, ar.organisationName);
        }
    }

}