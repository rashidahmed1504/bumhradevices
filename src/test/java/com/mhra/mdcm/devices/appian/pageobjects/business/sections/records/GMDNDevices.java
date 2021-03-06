package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.enums.PageHeaders;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
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
public class GMDNDevices extends _Page {

    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableHeadings;
    @FindBy(xpath = ".//h1[.='GMDN Devices']//following::tr")
    List<WebElement> listOfAllDevices;
    @FindBy(xpath = ".//td[2]")
    List<WebElement> listOfGmdnCode;
    @FindBy(xpath = ".//*[.='GMDN term']//following::tr//td[1]")
    List<WebElement> listOfDeviceTypes;

    //List of manufacturers using gmdn code
    @FindBy(xpath = ".//td[3]")
    List<WebElement> listOfOrganisationNames;

    //Search box
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(xpath = ".//span[@class='DropdownWidget---inline_label']")
    List<WebElement> listOfDropDownFilters;
    @FindBy(linkText = "Clear Filters")
    WebElement clearFilters;

    @Autowired
    public GMDNDevices(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By by = By.xpath(".//h1[.='" + expectedHeadings + "']");
        WaitUtils.waitForElementToBeClickable(driver, by , TIMEOUT_10_SECOND);
        WebElement heading = driver.findElement(by);
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='" + expectedHeadings + "']") , TIMEOUT_10_SECOND);

        if(expectedHeadings.contains(PageHeaders.PAGE_HEADERS_GMDN_DEVICES.header)){
            itemsDisplayed = listOfAllDevices.size() > 0;
        }

        return itemsDisplayed;
    }

    public GMDNDevices searchForAllDevices(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_10_SECOND);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new GMDNDevices(driver);
    }

    public String getARandomGMDNCode() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[2]"), TIMEOUT_5_SECOND);

        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfGmdnCode.size() - 1, false);
        WebElement gmdnCode = listOfGmdnCode.get(position);
        String gmdnLink = gmdnCode.getText();
        return gmdnLink;
    }


    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        try{
            //They have a hidden "a" tag in the page
            int actualCount = listOfAllDevices.size() - 1;
            atLeast1MatchFound = actualCount >= 1;
        }catch (Exception e){
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_10_SECOND);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableHeadings);
        return columnsNotFound;
    }

    public GMDNDevices clickOnARandomGMDNCode() {
        //String gmdnCode = getARandomGMDNCode();
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfGmdnCode.size() - 1, false);
        WebElement gmdnCode = listOfGmdnCode.get(position);
        gmdnCode = gmdnCode.findElement(By.tagName("a"));
        PageUtils.doubleClick(driver, gmdnCode);
        return new GMDNDevices(driver);

    }

    public boolean isListOfManufacturersVisible() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = listOfOrganisationNames.get(0);
        boolean isVisible = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, element, TIMEOUT_3_SECOND);
        }catch (Exception e){
            isVisible = false;
        }
        return isVisible;
    }

    public boolean isListOfManufacturersUsingDeviceTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_10_SECOND);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableHeadings);
        return columnsNotFound.size() == 0;
    }

    public GMDNDevices filterByDeviceType(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0) , deviceType, false);
        return new GMDNDevices(driver);
    }


    public boolean areAllDevicesOfType(String deviceType) {

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        boolean allMatched = true;
        for(WebElement el: listOfDeviceTypes){
            String text = el.getText();
            //log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.contains(deviceType);
                if (!allMatched) {
                    break;
                }
            }
        }
        return allMatched;
    }

    public GMDNDevices clearFilter() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_3_SECOND);
        clearFilters.click();
        return new GMDNDevices(driver);
    }



    public boolean areDevicesOfTypeVisible(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        boolean aMatchFound = false;
        for(WebElement el: listOfDeviceTypes){
            String text = el.getText();
            //log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                aMatchFound = text.contains(deviceType);
                if (aMatchFound) {
                    break;
                }
            }
        }
        return aMatchFound;
    }
}
