package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class _CreateManufacturerTestsData extends _Page {

    @FindBy(css = ".component_error")
    List<WebElement> errorMessages;

    //Organisation details
    @FindBy(xpath = ".//label[.='Organisation name']//following::input[1]")
    WebElement orgName;
    @FindBy(xpath = ".//label[contains(text(),'Address line 1')]//following::input[1]")
    WebElement addressLine1;
    @FindBy(xpath = ".//label[contains(text(),'Address line 2')]//following::input[1]")
    WebElement addressLine2;
    @FindBy(xpath = ".//label[contains(text(),'City')]//following::input[1]")
    WebElement townCity;
    @FindBy(xpath = ".//label[contains(text(),'Postcode')]//following::input[1]")
    WebElement postCode;
    @FindBy(xpath = ".//label[contains(text(),'Telephone')]//following::input[1]")
    WebElement telephone;
    @FindBy(xpath = ".//label[contains(text(),'Fax')]//following::input[1]")
    WebElement fax;
    @FindBy(xpath = ".//label[contains(text(),'Website')]//following::input[1]")
    WebElement website;
    @FindBy(xpath = ".//label[contains(text(),'Country')]//following::input[1]")
    WebElement country;

    //Contact Person Details
    @FindBy(xpath = ".//span[contains(text(),'Title')]//following::div[@role='listbox']")
    WebElement title;
    @FindBy(xpath = ".//label[.='First name']//following::input[1]")
    WebElement firstName;
    @FindBy(xpath = ".//label[.='Last name']//following::input[1]")
    WebElement lastName;
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement emailAddress;
    @FindBy(xpath = ".//label[.='Email']//following::input[2]")
    WebElement phoneNumber;

    //Letter of designation
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    WebElement fileUpload;

    //Submit and cancel
    @FindBy(xpath = ".//button[contains(text(),'Declare devices')]")
    WebElement btnDeclareDevices;
    @FindBy(xpath = ".//button[.='Next']")
    WebElement next;
    @FindBy(xpath = ".//button[.='Cancel']")
    WebElement cancel;


    @Autowired
    public _CreateManufacturerTestsData(WebDriver driver) {
        super(driver);
    }

    /**
     * HELPS TESTERS CREATE TEST DATA ON THE GO
     * @param ar
     * @return
     */
    public AddDevices createTestOrganisation(ManufacturerRequestDO ar) throws Exception {
        WaitUtils.nativeWaitInSeconds(3);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".PickerWidget---picker_value"), TIMEOUT_10_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
        orgName.sendKeys(ar.organisationName);
        //PageUtils.selectCountryFromAutoSuggests(driver, ".gwt-SuggestBox", ar.country, false);
        boolean exception = false;
        try {
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", ar.country, true);
        }catch (Exception e){
            exception = true;
        }

        //Organisation details
        WaitUtils.waitForElementToBeClickable(driver, addressLine1, TIMEOUT_DEFAULT, false);
        addressLine1.clear();
        addressLine1.sendKeys(ar.address1);
        addressLine2.sendKeys(ar.address2);
        townCity.sendKeys(ar.townCity);
        postCode.sendKeys(ar.postCode);
        telephone.sendKeys(ar.telephone);
        fax.sendKeys(ar.fax);
        website.sendKeys(ar.website);

        //Contact Person Details
        try {
            PageUtils.singleClick(driver, title);
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//div[contains(text(), '"+ ar.title + "')]"), TIMEOUT_3_SECOND, false);
            WebElement titleToSelect = driver.findElement(By.xpath(".//div[contains(text(), '"+ ar.title + "')]"));
            PageUtils.singleClick(driver, titleToSelect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        firstName.sendKeys(ar.firstName);
        lastName.sendKeys(ar.lastName);
        jobTitle.sendKeys(ar.jobTitle);
        phoneNumber.sendKeys(ar.phoneNumber);
        emailAddress.sendKeys(ar.email);

        if(exception){
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", ar.country, true);
        }

        //Upload letter of designation
        String fileName = "DesignationLetter1.pdf";
        if(!ar.isManufacturer){
            fileName = "DesignationLetter2.pdf";
        }
        PageUtils.uploadDocument(fileUpload, fileName, 1, 2);

        //Submit form : remember to verify
        try{
            btnDeclareDevices.click();
        }catch (Exception e){
            next.click();
        }

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        return new AddDevices(driver);
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3, false);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }

    public List<String> getListOfAutosuggestionsFor(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver,TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".PickerWidget---picker_value"), TIMEOUT_5_SECOND, false);
        List<String> matchesFromAutoSuggests = PageUtils.getListOfMatchesFromAutoSuggests(driver, By.cssSelector(".PickerWidget---picker_value"), searchTerm);
        System.out.println(matchesFromAutoSuggests);
        return matchesFromAutoSuggests;
    }
}