package com.mhra.mdcm.devices.appian.steps.d1.email;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.email.GmailEmail;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.java.en.And;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class EmailSteps extends CommonSteps {


    @And("^I should received an email for stored manufacturer with heading \"([^\"]*)\"$")
    public void iShouldReceivedAnEmailForStoredManufacturerWithHeading(String emailHeading) throws Throwable {
        String org = (String) scenarioSession.getData(SessionKey.organisationName);
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.readMessageForSpecifiedOrganisations(7, 10, emailHeading, accountNameOrReference);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                foundMessage = true;
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email and its outside of selenium scope
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 30);

        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
        Assert.assertThat("Organisation Name Expected : " + org, messageBody.contains(org), Matchers.is(true));
    }

    @And("^I should received an email for stored manufacturer with heading \"([^\"]*)\" and stored application identifier$")
    public void iShouldReceivedAnEmailForStoredManufacturerWithHeadingAndApplicationIdentifier(String emailHeading) throws Throwable {
        String org = (String) scenarioSession.getData(SessionKey.organisationName);
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);

        boolean foundMessage = false;
        int attempt = 0;
        do {
            foundMessage = GmailEmail.isMessageReceivedWithHeading(7, 10, emailHeading, accountNameOrReference);

            //Break from loop if invoices read from the email server
            if (foundMessage) {
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email and its outside of selenium scope
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 30);

        Assert.assertThat("Found message with heading : " + emailHeading + ", And reference : " + accountNameOrReference, foundMessage, Matchers.is(true));
    }

    @And("^I should received an email for stored account with heading \"([^\"]*)\"$")
    public void iShouldReceivedAnEmailForStoredAccountWithHeading(String emailHeading) throws Throwable {

        String org = (String) scenarioSession.getData(SessionKey.newAccountName);
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.readMessageForSpecifiedOrganisations(7, 10, emailHeading, accountNameOrReference);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                foundMessage = true;
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 30);

        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
        Assert.assertThat("Organisation Name Expected : " + org, messageBody.contains(org), Matchers.is(true));
    }

}
