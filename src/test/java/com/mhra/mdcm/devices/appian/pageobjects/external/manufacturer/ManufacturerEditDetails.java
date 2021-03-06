package com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.MyAccountPage;
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
public class ManufacturerEditDetails extends _Page {


    //ORGANISATION DETAILS
    @FindBy(xpath = ".//label[.='Organisation name']//following::input[1]")
    WebElement orgName;
    @FindBy(xpath = ".//label[.='Address line 1']//following::input[1]")
    WebElement orgAddressLine1;
    @FindBy(xpath = ".//label[contains(text(),'Address line 2')]//following::input[1]")
    WebElement orgAddressLine2;
    @FindBy(xpath = ".//label[contains(text(),'City')]//following::input[1]")
    WebElement orgCityTown;
    @FindBy(xpath = ".//label[.='Postcode']//following::input[1]")
    WebElement orgPostCode;
    @FindBy(xpath = ".//label[text()='Postcode']//following::input[@type='text'][2]")
    WebElement orgTelephone;
    @FindBy(xpath = ".//label[contains(text(),'Website')]//following::input[1]")
    WebElement webSite;
    @FindBy(xpath = ".//span[contains(text(),'Address type')]//following::p[1]")
    WebElement addressType;

    //Contact details
    @FindBy(xpath = ".//span[contains(text(),'Title')]//following::div[@role='listbox']")
    WebElement title;
    @FindBy(xpath = ".//label[.='First name']//following::input[1]")
    WebElement firstName;
    @FindBy(xpath = ".//label[.='Last name']//following::input[1]")
    WebElement lastName;
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement email;
    @FindBy(xpath = ".//label[.='Email']//following::input[2]")
    WebElement telephone;

    //Error message
    @FindBy(css = ".component_error")
    List <WebElement> errorMessages;

    //Submit or cancel button
    @FindBy(xpath = ".//button[.='Yes']")
    WebElement confirmYes;
    @FindBy(xpath = ".//button[.='No']")
    WebElement confirmNo;
    @FindBy(xpath = ".//button[contains(text(),'Save')]")
    List<WebElement> save;
    @FindBy(xpath = ".//button[contains(text(),'Submit')]")
    WebElement submitBtn;
    @FindBy(xpath = ".//button[contains(text(),'Cancel')]")
    WebElement cancel;


    @Autowired
    public ManufacturerEditDetails(WebDriver driver) {
        super(driver);
    }


    public ManufacturerEditDetails updateFollowingFields(String keyValuePairToUpdate, AccountRequestDO updatedData) {

        WaitUtils.waitForElementToBeClickable(driver, submitBtn, TIMEOUT_5_SECOND);
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for (String pairs : dataPairs) {

            String key = pairs;
            boolean orgNameUpdated = false;

            //Organisation details
            if (key.equals("org.name")) {
                PageUtils.updateElementValue(driver, orgName, updatedData.organisationName, TIMEOUT_5_SECOND);
                orgNameUpdated = true;
            }else if (key.equals("org.address1")) {
                PageUtils.updateElementValue(driver, orgAddressLine1, updatedData.address1, TIMEOUT_5_SECOND);
            }else if (key.equals("org.address2")) {
                PageUtils.updateElementValue(driver, orgAddressLine2, updatedData.address2, TIMEOUT_5_SECOND);
            }else if (key.equals("org.city")) {
                PageUtils.updateElementValue(driver, orgCityTown, updatedData.townCity, TIMEOUT_5_SECOND);
            }else if (key.equals("org.postcode")) {
                PageUtils.updateElementValue(driver, orgPostCode, updatedData.postCode, TIMEOUT_5_SECOND);
            }else if (key.equals("org.country")) {
                try {
                    PageUtils.selectFromAutoSuggestedListItems(driver, ".PickerWidget---picker_value", updatedData.country, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (key.equals("org.telephone")) {
                PageUtils.updateElementValue(driver, orgTelephone, updatedData.telephone, TIMEOUT_5_SECOND);
            }else if (key.equals("org.website")) {
                if(orgNameUpdated)
                PageUtils.updateElementValue(driver, webSite, updatedData.website, TIMEOUT_5_SECOND);
            }

            //Contact details
            if (key.equals("contact.title")) {
                PageUtils.selectFromDropDown(driver, title, updatedData.title, false);
            }else if (key.equals("contact.firstname")) {
                PageUtils.updateElementValue(driver, firstName, updatedData.firstName, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.lastname")) {
                PageUtils.updateElementValue(driver, lastName, updatedData.lastName, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.job.title")) {
                PageUtils.updateElementValue(driver, jobTitle, updatedData.jobTitle, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.email")) {
                PageUtils.updateElementValue(driver, email, updatedData.email, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.telephone")) {
                PageUtils.updateElementValue(driver, telephone, updatedData.telephone, TIMEOUT_5_SECOND);
            }
        }

        PageUtils.doubleClick(driver, submitBtn);

        return new ManufacturerEditDetails(driver);
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }


    public MyAccountPage saveChanges(boolean saveChanges) {
        WaitUtils.waitForElementToBeClickable(driver, cancel, TIMEOUT_10_SECOND);
        if(saveChanges){
            save.get(1).click();
        }else{
            cancel.click();
        }
        return new MyAccountPage(driver);
    }


    public boolean isAddressTypeEditable() {
        boolean isEditable = true;
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, addressType, TIMEOUT_5_SECOND);
        try{
            addressType.sendKeys("not editable");
        }catch (Exception e){
            isEditable = false;
        }
        return isEditable;
    }


    public ManufacturerDetails confirmChanges(boolean confirm) {
        WaitUtils.waitForElementToBeClickable(driver, confirmYes, TIMEOUT_10_SECOND);
        if(confirm){
            confirmYes.click();
        }else{
            confirmNo.click();
        }
        return new ManufacturerDetails(driver);
    }

}
