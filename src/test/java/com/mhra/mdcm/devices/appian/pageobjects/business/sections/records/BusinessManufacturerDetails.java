package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class BusinessManufacturerDetails extends _Page {

    //Headings
    @FindBy(xpath = ".//h1")
    WebElement heading;
    @FindBy(xpath = ".//h2")
    WebElement subHeading;

    //Links to other sections like devices, documents
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement editAccountInfoLink;
    @FindBy(xpath = ".//span[contains(text(),'Letter of ')]//following::a")
    WebElement linkLetterOfDesignation;

    //PARD message
    @FindBy(xpath = ".//*[contains(text(),'PARD selection')]//following::p[1]")
    WebElement pardMessage;

    //Unregister a registered device
    @FindBy(xpath = ".//button[contains(text(),'Unregister Manufacturer')]")
    WebElement btnUnregisterManufacturer;
    @FindBy(xpath = ".//button[contains(text(),'Unregister')]")
    WebElement btnUnregister;
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    WebElement fileUpload;

    //HTML Alert box
    @FindBy(xpath = ".//button[.='Yes']")
    WebElement btnYes;
    @FindBy(xpath = ".//button[.='No']")
    WebElement btnNo;

    //Unregister reason
    @FindBy(xpath = ".//label[contains(text(),'Ceased Trading')]")
    WebElement rbCeasedTrading;
    @FindBy(xpath = ".//label[contains(text(),'No Longer Represented')]")
    WebElement rbNoLongerRpresented;

    //Unregisteration notifications
    @FindBy(xpath = ".//h2[contains(text(),'Send unregisteration')]//following::label[1]")
    WebElement cbToAuthorisedRep;
    @FindBy(xpath = ".//h2[contains(text(),'Send unregisteration')]//following::label[2]")
    WebElement cbToManufacturer;

    //Unregister reference number
    @FindBy(xpath = ".//*[contains(text(),'successfully submitted to MHRA')]//following::p[1]")
    WebElement txtApplicationReferenceNumber;


    //Rejection reason
    @FindBy(xpath = ".//*[.='Reasons']//following::input[1]")
    WebElement reasonAccountAlreadyExists;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[2]")
    WebElement reasonNotRegisteredInUk;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[3]")
    WebElement reasonNoAuthorisationEvidenceProvided;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[4]")
    WebElement reasonNonQualifyingParty;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[5]")
    WebElement other;
    @FindBy(css = "div>textarea")
    WebElement commentArea;
    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;


    //Rejection reason CFS new manufacturer
    @FindBy(xpath = ".//button[.='Reject new manufacturer']")
    WebElement rejectCFSNewManufacturer;
    @FindBy(xpath = ".//button[.='Reject order']")
    WebElement rejectCFSNewOrder;
    @FindBy(xpath = ".//*[.='Select a rejection reason']//following::input[3]")
    WebElement cfsReasonOther;
    @FindBy(xpath = ".//*[.='Select a rejection reason']//following::input[5]")
    WebElement cfsReasonSubmittedInError;

    //Application approve reject buttons
    @FindBy(xpath = ".//button[contains(text(), 'Approve account')]")
    WebElement btnApproveNewAccount;
    @FindBy(xpath = ".//button[contains(text(), 'Reject account')]")
    WebElement btnRejectNewAccount;
    @FindBy(xpath = ".//button[contains(text(), 'Approve manufacturer')]")
    WebElement btnApproveManufacturer;
    @FindBy(xpath = ".//button[contains(text(), 'Reject manufacturer')]")
    WebElement btnRejectManufacturer;
    @FindBy(xpath = ".//button[contains(text(), 'Reject order')]")
    WebElement btnRejectOrder;
    @FindBy(xpath = ".//button[contains(text(), 'Approve all devices')]")
    WebElement btnApproveAllDevices;
    @FindBy(xpath = ".//button[contains(text(), 'Change')]")
    WebElement btnChangeDecision;
    @FindBy(xpath = ".//button[contains(text(), 'Save')]")
    WebElement btnSave;
    @FindBy(xpath = ".//button[contains(text(), 'Complete the application')]")
    WebElement btnCompleteTheApplication;

    //BACS confirm payment
    @FindBy(xpath = ".//button[contains(text(), 'Confirm Payment')]")
    WebElement btnConfirmPayment;
    @FindBy(xpath = ".//*[contains(text(), 'Payment received')]/following::input[1]")
    WebElement tbxPaymentDate;
    @FindBy(xpath = ".//*[contains(text(), 'Payment received')]/following::input[2]")
    WebElement tbxPaymentHour;


    //Reassign to someone
    @FindBy(xpath = ".//button[contains(text(), 'Assign to myself')]")
    WebElement btnAssignToMe;
    @FindBy(xpath = ".//button[text()='Yes']")
    WebElement btnConfirmYesAssignToMe;
    @FindBy(xpath = ".//label[contains(text(), 'Nobody')]")
    WebElement btnConfirmNoAssignToMe;
    @FindBy(xpath = ".//button[contains(text(), 'Assign to colleague')]")
    WebElement btnAssignToColleague;
    @FindBy(xpath = ".//button[text()='Assign']")
    WebElement btnAssign;
    @FindBy(xpath = ".//label[contains(text(), 'Nobody')]")
    WebElement cbxNobody;
    @FindBy(xpath = ".//label[contains(text(), 'of my colleague')]")
    WebElement cbxOneOfMyColleague;
    @FindBy(xpath = ".//*[contains(text(), 'Select user')]/following::input")
    WebElement tbxColleagueSearchBox;

    //Tabs summary, devices and products, history
    @FindBy(xpath = ".//*[contains(text(),'Devices &')]")
    WebElement tabDevicesAndProductDetails;
    @FindBy(partialLinkText = "Summary")
    WebElement tabSummary;

    //ORGANISATION DETAILS verify
    @FindBy(xpath = ".//span[.='Manufacturer name']//following::p[1]")
    WebElement manName;
    @FindBy(xpath = ".//span[.='Manufacturer address']//following::p[1]")
    WebElement manAddressFull;
    @FindBy(xpath = ".//span[contains(text(),'Manufacturer telephone')]//following::p[1]")
    WebElement manTelephone;

    //Payment details data for BACS and WorldPay
    @FindBy(xpath = ".//*[contains(text(),'Payment Details')]//following::p[2]")
    WebElement paymentAmount;
    @FindBy(xpath = ".//*[contains(text(),'Payment Details')]//following::p[3]")
    WebElement paymentType;
    @FindBy(xpath = ".//*[contains(text(),'Payment Details')]//following::p[6]")
    WebElement paymentProof;

    @Autowired
    public BusinessManufacturerDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isManufacturerHeadingCorrect(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean contains = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, heading, TIMEOUT_10_SECOND);
            contains = heading.getText().contains(searchTerm);
        }catch (Exception e){
            WaitUtils.waitForElementToBeClickable(driver, tabDevicesAndProductDetails, TIMEOUT_3_SECOND);
            contains = subHeading.getText().contains(searchTerm);
        }
        return contains;
    }

    public BusinessDeviceDetails clickOnDevicesLink(String link) {
        WaitUtils.waitForElementToBeClickable(driver, tabDevicesAndProductDetails, TIMEOUT_5_SECOND);
        tabDevicesAndProductDetails.click();
        return new BusinessDeviceDetails(driver);
    }


    public EditAccount gotoEditAccountInformation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, editAccountInfoLink, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, editAccountInfoLink);
        //editAccountInfoLink.click();
        return new EditAccount(driver);
    }

    public boolean isPARDMessaageCorrect(String expectedMessage) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, pardMessage, TIMEOUT_10_SECOND);
        String msg = pardMessage.getText();
        log.info("Message : " + msg);
        boolean found = msg.contains(expectedMessage);
        return found;
    }

    public BusinessManufacturerDetails clickUnregisterManufacturerBtn() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnUnregisterManufacturer, TIMEOUT_10_SECOND);
        btnUnregisterManufacturer.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails submitUnRegistrationWithReason(String reason, boolean confirmUnregisttratoin) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, btnUnregister, TIMEOUT_10_SECOND);

        //Select a reason
        if(reason.contains("Ceased Trading")){
            rbCeasedTrading.click();
        }else if(reason.contains("No Longer Represented")){
            rbNoLongerRpresented.click();
            PageUtils.uploadDocument(fileUpload, "LetterOfCancellation1.pdf", 1, 2);
        }

        //Select send notifications to
        cbToAuthorisedRep.click();
        cbToManufacturer.click();

        //Click unregister button and confirm
        WaitUtils.waitForElementToBeClickable(driver, btnUnregister, TIMEOUT_10_SECOND);
        btnUnregister.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails clickAlertButtonYes() {
        WaitUtils.waitForElementToBeClickable(driver, btnYes, TIMEOUT_5_SECOND);
        btnYes.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails clickAlertButtonNo() {
        WaitUtils.waitForElementToBeClickable(driver, btnNo, TIMEOUT_5_SECOND);
        btnNo.click();
        return new BusinessManufacturerDetails(driver);
    }


    public BusinessManufacturerDetails assignAWIPTaskToMe() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAssignToMe, TIMEOUT_60_SECOND);
        btnAssignToMe.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails assignAWIPTaskToColleague() {
        WaitUtils.waitForElementToBeClickable(driver, btnAssignToColleague, TIMEOUT_10_SECOND);
        btnAssignToColleague.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails confirmAWIPIAssignment(boolean clickYes) {
        if(PageUtils.isVisible(driver, btnConfirmYesAssignToMe, TIMEOUT_3_SECOND)) {
            WaitUtils.waitForElementToBeClickable(driver, btnConfirmYesAssignToMe, TIMEOUT_10_SECOND);
            if (clickYes) {
                btnConfirmYesAssignToMe.click();
            } else {
                btnConfirmNoAssignToMe.click();
            }
        }
        return new BusinessManufacturerDetails(driver);
    }


    public BusinessManufacturerDetails rejectAWIPNewAccountRegistration() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnRejectNewAccount, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, btnRejectNewAccount);
        log.info("New account registration : REJECTED");
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails approveAWIPTaskNewAccount() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnApproveNewAccount, TIMEOUT_60_SECOND);
        PageUtils.doubleClick(driver, btnApproveNewAccount);
        log.info("Task should be approved now");
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails approveAWIPManufacturerTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnApproveManufacturer, TIMEOUT_60_SECOND);
        PageUtils.doubleClick(driver, btnApproveManufacturer);
        log.info("Approved the manufacturer");
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails rejectAWIPManufacturerTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnRejectManufacturer, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, btnRejectManufacturer);
        log.info("Reject the manufacturer");
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails rejectAWIPCFSOrder() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnRejectOrder, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, btnRejectOrder);
        log.info("Reject the CFS Order");
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails approveAWIPAllDevices() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnApproveAllDevices, TIMEOUT_60_SECOND);
        PageUtils.doubleClick(driver, btnApproveAllDevices);
        log.info("Approved all the devices");
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails completeTheApplication() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnCompleteTheApplication, TIMEOUT_60_SECOND);
        PageUtils.doubleClick(driver, btnCompleteTheApplication);
        log.info("Application completed");
        return new BusinessManufacturerDetails(driver);
    }
    public BusinessManufacturerDetails confirmPayment() {
        WaitUtils.waitForElementToBeClickable(driver, btnConfirmPayment, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, btnConfirmPayment);
        log.info("Confirm Payment");
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails enterDateAndTimeOfPayment() {
        WaitUtils.waitForElementToBeClickable(driver, tbxPaymentDate, TIMEOUT_10_SECOND);
        tbxPaymentDate.sendKeys(RandomDataUtils.getDateInFutureDays(0), Keys.TAB);
        tbxPaymentHour.sendKeys("01:00", Keys.TAB);

        WaitUtils.waitForElementToBeClickable(driver, btnSave, TIMEOUT_15_SECOND);
        btnSave.click();

        return new BusinessManufacturerDetails(driver);
    }

    public boolean isButtonVisibleWithText(String button, int timeout) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        button = button.toLowerCase();
        if(button.contains("assign to myself"))
            return PageUtils.isElementClickable(driver, btnAssignToMe, timeout);
        else if(button.contains("assign to colleague"))
            return PageUtils.isElementClickable(driver, btnAssignToColleague, timeout);
        else if(button.contains("approve manufacturer"))
            return PageUtils.isElementClickable(driver, btnApproveManufacturer, timeout);
        else if(button.contains("reject manufacturer"))
            return PageUtils.isElementClickable(driver, btnRejectManufacturer, timeout);
        else if(button.contains("change decision"))
            return PageUtils.isElementClickable(driver, btnChangeDecision, timeout);

        return true;
    }

    public boolean isButtonNotVisibleWithText(String button, int timeout) {
        if(button.contains("Assign to myself"))
            return PageUtils.isElementNotVisible(driver, btnAssignToMe, timeout);
        else if(button.contains("Assign to colleague"))
            return PageUtils.isElementNotVisible(driver, btnAssignToColleague, timeout);
        else if(button.contains("Approve manufacturer"))
            return PageUtils.isElementNotVisible(driver, btnApproveManufacturer, timeout);
        else if(button.contains("Reject manufacturer"))
            return PageUtils.isElementNotVisible(driver, btnRejectManufacturer, timeout);
        return true;
    }


    public BusinessManufacturerDetails enterRejectionReason(String reason, String randomTestComment) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if (reason != null) {
            if (reason.contains("Other")) {
                //Comment is mandatory
                WaitUtils.waitForElementToBeClickable(driver, other, TIMEOUT_10_SECOND);
                other.click();
            } else if (reason.contains("Account already exists")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonAccountAlreadyExists, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonAccountAlreadyExists);
            } else if (reason.contains("Not registered in the UK")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonNotRegisteredInUk, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonNotRegisteredInUk);
            } else if (reason.contains("No authorisation evidence provided")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonNoAuthorisationEvidenceProvided, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonNoAuthorisationEvidenceProvided);
            } else if (reason.contains("Non-qualifying party")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonNonQualifyingParty, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonNonQualifyingParty);
            } else {
                //They have changed rejection process: The options have disappeared

            }
        }

        //Enter comment
        WaitUtils.waitForElementToBeClickable(driver, commentArea, TIMEOUT_10_SECOND);
        commentArea.sendKeys(randomTestComment);

        //Submit rejection
        PageUtils.doubleClick(driver, submitBtn);
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails enterManufacturerRejectionReason(String reason, String randomTestComment) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if (reason != null) {
            if (reason.contains("Other")) {
                //Comment is mandatory
                WaitUtils.waitForElementToBeClickable(driver, cfsReasonOther, TIMEOUT_10_SECOND);
                other.click();
            } else if (reason.contains("Submitted in error")) {
                WaitUtils.waitForElementToBeClickable(driver, cfsReasonSubmittedInError, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, cfsReasonSubmittedInError);
            }
        }

        //Enter comment
        WaitUtils.waitForElementToBeClickable(driver, commentArea, TIMEOUT_10_SECOND);
        commentArea.sendKeys(randomTestComment);

        //Submit rejection
        PageUtils.doubleClick(driver, rejectCFSNewManufacturer);
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails enterCFSOrderRejectionReason(String reason, String randomTestComment) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if (reason != null) {
            if (reason.contains("Other")) {
                //Comment is mandatory
                WaitUtils.waitForElementToBeClickable(driver, cfsReasonOther, TIMEOUT_10_SECOND);
                other.click();

                //Enter comment
                WaitUtils.waitForElementToBeClickable(driver, commentArea, TIMEOUT_10_SECOND);
                commentArea.sendKeys(randomTestComment);
            } else if (reason.contains("Submitted in error")) {
                WaitUtils.waitForElementToBeClickable(driver, cfsReasonSubmittedInError, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, cfsReasonSubmittedInError);
            }
        }

        //Submit rejection
        PageUtils.doubleClick(driver, rejectCFSNewOrder);
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessDeviceDetails clickOnDeviceAndProductsTab() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tabDevicesAndProductDetails, TIMEOUT_10_SECOND);
        tabDevicesAndProductDetails.click();
        return new BusinessDeviceDetails(driver);
    }

    public BusinessManufacturerDetails clickOnSummaryTab() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tabSummary, TIMEOUT_10_SECOND);
        tabSummary.click();
        return new BusinessManufacturerDetails(driver);
    }



    public BusinessManufacturerDetails assigntToNobody() {
        WaitUtils.waitForElementToBeClickable(driver, cbxNobody, TIMEOUT_10_SECOND);
        cbxNobody.click();
        btnAssign.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails assignToColleague(String assignTo) {
        WaitUtils.waitForElementToBeClickable(driver, cbxOneOfMyColleague, TIMEOUT_10_SECOND);
        cbxOneOfMyColleague.click();
        WaitUtils.waitForElementToBeClickable(driver, tbxColleagueSearchBox, TIMEOUT_10_SECOND);
        try {
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", assignTo, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnAssign.click();
        return new BusinessManufacturerDetails(driver);
    }

    public boolean isManufacturerDetailCorrect(ManufacturerRequestDO manufacaturerData) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, manName, TIMEOUT_10_SECOND);
        boolean isValid = manName.getText().contains(manufacaturerData.organisationName);
        if (isValid) {
            isValid = manAddressFull.getText().contains(manufacaturerData.address1);
            log.info("Address is valid");
        }
        if (isValid) {
            isValid = manAddressFull.getText().contains(manufacaturerData.postCode);
            log.info("Postcode is valid");
        }
        if (isValid) {
            isValid = manAddressFull.getText().contains(manufacaturerData.country);
            log.info("Country is valid");
        }

        if (isValid) {
            isValid = manTelephone.getText().contains(manufacaturerData.telephone);
            log.info("Telephone is valid");
        }

        return isValid;
    }

    public BusinessManufacturerDetails clickOnChangeDecisionButton() {
        WaitUtils.waitForElementToBeClickable(driver, btnChangeDecision, TIMEOUT_5_SECOND);
        btnChangeDecision.click();
        return new BusinessManufacturerDetails(driver);
    }

    public boolean isDesignationLetterAttached() {
        boolean clickable = PageUtils.isElementClickable(driver, linkLetterOfDesignation, TIMEOUT_10_SECOND);
        return clickable;
    }

    public String getApplicationReferenceNumber() {
        WaitUtils.waitForElementToBeClickable(driver, txtApplicationReferenceNumber, TIMEOUT_5_SECOND);
        String text = txtApplicationReferenceNumber.getText();
        int start = text.indexOf("ber is");
        String ref = text.substring(start+7, start+21);
        return ref;
    }

    public boolean isPaymentDetailsCorrect(String paymentMethodTxt, String paymentDocTxt, String amountTxt) {
        boolean isValid = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, paymentType, TIMEOUT_10_SECOND);
        String pt = paymentType.getText();
        String pd = paymentProof.getText();
        String pa = paymentAmount.getText();

        if(!pt.contains(paymentMethodTxt)) {
            log.info("Payment type incorrect : " + pt);
            isValid = false;
        }else if(!pd.contains(paymentDocTxt)) {
            log.info("Payment doc incorrect : " + pd);
            isValid = false;
        }else if(!pa.contains(amountTxt)) {
            log.info("Payment amount incorrect : " + pa);
            isValid = false;
        }

        return isValid;

    }
}
