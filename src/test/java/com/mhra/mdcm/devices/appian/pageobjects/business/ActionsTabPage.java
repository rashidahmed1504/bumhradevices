package com.mhra.mdcm.devices.appian.pageobjects.business;


import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class ActionsTabPage extends _Page {

    @FindBy(partialLinkText = "Create Test Account")
    WebElement linkCreateTestAccount;

    @FindBy(xpath = ".//h3[contains(text(), 'Application complete')]")
    WebElement txtApplicationComplete;
    @FindBy(xpath = ".//h3[contains(text(), 'Application complete')]/following::h4[1]")
    WebElement txtApplicationReference;

    @Autowired
    public ActionsTabPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInActionsPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkCreateTestAccount, TIMEOUT_15_SECOND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isApplicationSubmittedSuccessfully() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            WaitUtils.waitForElementToBeClickable(driver, txtApplicationComplete, TIMEOUT_15_SECOND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public _CreateAccountTestHarnessPage gotoTestsHarnessPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkCreateTestAccount, TIMEOUT_15_SECOND);
            WaitUtils.waitForAlert(driver, 3);
        }catch (Exception e) {}

        linkCreateTestAccount.click();
        return new _CreateAccountTestHarnessPage(driver);
    }

    public ActionsTabPage refreshThePage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkCreateTestAccount, TIMEOUT_10_SECOND);
        driver.navigate().refresh();
        return new ActionsTabPage(driver);
    }

    public String getApplicationReferenceNumber() {
        return txtApplicationReference.getText();
    }
}
