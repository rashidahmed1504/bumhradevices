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
public class Accounts extends _Page {

    @FindBy(xpath = ".//div[.='Status']//following::tr")
    List<WebElement> listOfAccounts;
    @FindBy(xpath = ".//td[1]")
    List<WebElement> listOfAccountsNames;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;
    @FindBy(xpath = ".//td[3]")
    List<WebElement> listOfOrganisationRoles;

    //TABLE Heading
    @FindBy(xpath = ".//th[1]")
    WebElement thOrganisationName;

    //Search box and filters
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(xpath = ".//span[@class='DropdownWidget---inline_label']")
    List<WebElement> listOfDropDownFilters;
    @FindBy(linkText = "Clear Filters")
    WebElement clearFilters;
    @FindBy(xpath = ".//button[contains(text(),'Search')]")
    WebElement btnSearch;


    @Autowired
    public Accounts(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By locator = By.xpath(".//h1[.='" + expectedHeadings + "']");
        WaitUtils.waitForElementToBeClickable(driver, locator, TIMEOUT_10_SECOND);
        WebElement heading = driver.findElement(locator);
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='" + expectedHeadings + "']"), TIMEOUT_10_SECOND);

        if (expectedHeadings.contains(PageHeaders.PAGE_HEADERS_ACCOUNTS.header)) {
            itemsDisplayed = listOfAccounts.size() > 0;
        }

        return itemsDisplayed;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th"), TIMEOUT_10_SECOND);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableColumns);
        return columnsNotFound;
    }

    public Accounts searchForAccount(String searchTerm) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_30_SECOND);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new Accounts(driver);
    }

    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.linkText("Clear Filters"), TIMEOUT_10_SECOND);
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(searchText), TIMEOUT_5_SECOND);
            int actualCount = (listOfAccounts.size());
            atLeast1MatchFound = actualCount >= 1;
        } catch (Exception e) {
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    /**
     * NOTE THERE MAY BE MORE THAN 1 LINK PER ROW
     *
     * @return
     */
    public String getARandomAccount() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//th[1]"), TIMEOUT_15_SECOND);

        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAccountsNames.size() - 1, false);
        WebElement accountLinks = listOfAccountsNames.get(position);
        String accountName = accountLinks.getText();
        return accountName;
    }

    public String getARandomAccountWithText(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//th[1]"), TIMEOUT_5_SECOND);

        boolean found = false;
        int count = 0;
        String accountName = "";
        do {
            count++;
            int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAccountsNames.size() - 1, false);
            WebElement accountLinks = listOfAccountsNames.get(position);
            accountName = accountLinks.getText();
            if (accountName.contains(name)) {
                found = true;
            }
        } while (!found && count <= 3);

        return accountName;
    }

    public ViewAccount viewSpecifiedAccount(String randomAccountName) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(randomAccountName), TIMEOUT_10_SECOND);
        WebElement accountLinks = driver.findElement(By.partialLinkText(randomAccountName));
        //accountLinks.click();
        PageUtils.doubleClick(driver, accountLinks);
        return new ViewAccount(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='Accounts']"), TIMEOUT_5_SECOND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Accounts filterByOrganistionRole(String organisationRole) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0), organisationRole, false);
        return new Accounts(driver);
    }

    public Accounts filterByRegisteredStatus(String status) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(1), status, false);
        return new Accounts(driver);
    }

    public Accounts sortBy(String tableHeading, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (tableHeading.equals("Organisation name")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                WaitUtils.waitForElementToBeClickable(driver, thOrganisationName, TIMEOUT_10_SECOND);
                thOrganisationName.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            }
        }

        return new Accounts(driver);
    }

    public boolean areAllOrganisationRoleOfType(String organisationType) {
        organisationType = organisationType.toLowerCase();
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_30_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, listOfOrganisationRoles.get(0), TIMEOUT_10_SECOND);
        WaitUtils.nativeWaitInSeconds(5);

        boolean allMatched = true;
        for (WebElement el : listOfOrganisationRoles) {
            String text = el.getText().toLowerCase();
            log.info(text);
            if (!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.contains(organisationType) || text.equals("");
                ;
                if (!allMatched) {
                    break;
                }
            }
        }

        return allMatched;
    }

    public boolean isOrderedAtoZ() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[1]"), TIMEOUT_10_SECOND);
        boolean isOrderedAToZ = PageUtils.isOrderedAtoZ(listOfAccountsNames, 2);
        return isOrderedAToZ;
    }

    public Accounts clearFilterByOrganisation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_3_SECOND);
        clearFilters.click();
        return new Accounts(driver);
    }

    public boolean areOrganisationOfRoleVisible(String organisationType, String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = clearFilters;
        if (searchTerm == null)
            element = btnSearch;
        WaitUtils.waitForElementToBeClickable(driver, element, TIMEOUT_10_SECOND);

        WaitUtils.nativeWaitInSeconds(5);
        boolean aMatchFound = false;
        for (WebElement el : listOfOrganisationRoles) {
            String text = el.getText();
            log.info(text);
            if (!text.contains("revious") && !text.contains("ext")) {
                aMatchFound = text.contains(organisationType) || text.equals("");
                ;
                if (aMatchFound) {
                    break;
                }
            }
        }
        return aMatchFound;
    }

    public BusinessManufacturerDetails viewManufacturerByText(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        WebElement manufacturer = null;
        boolean found = false;
        do {
            int randomNumberBetween = RandomDataUtils.getSimpleRandomNumberBetween(0, listOfAccountsNames.size() - 1, false);
            if (listOfAccountsNames.size() == 1)
                randomNumberBetween = 0;
            WebElement element = listOfAccountsNames.get(randomNumberBetween);
            String orgName = element.getText();
            if (orgName.contains(searchTerm)) {
                manufacturer = element.findElement(By.tagName("a"));
                found = true;
            }
        } while (!found);

        //Click the manufacturer name
        if (manufacturer != null) {
            PageUtils.doubleClick(driver, manufacturer);
            //manufacturer.click();
        } else {
            throw new RuntimeException("No manufacturer found for search term : " + searchTerm);
        }

        return new BusinessManufacturerDetails(driver);
    }

    public boolean isSearchingCompleted(String searchTerm) {
        boolean seachingCompleted = false;
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            if (searchTerm != null && !searchTerm.equals(""))
                WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_30_SECOND);
            seachingCompleted = true;
        } catch (Exception e) {
        }
        return seachingCompleted;
    }
}
