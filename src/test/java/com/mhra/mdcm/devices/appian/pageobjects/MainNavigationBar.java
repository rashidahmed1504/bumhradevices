package com.mhra.mdcm.devices.appian.pageobjects;


import com.mhra.mdcm.devices.appian.pageobjects.business.*;
import com.mhra.mdcm.devices.appian.pageobjects.external.MyAccountPage;
import com.mhra.mdcm.devices.appian.pageobjects.external.ExternalHomePage;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author TPD_Auto
 */
@Component
public class MainNavigationBar extends _Page {

    @FindBy(partialLinkText = "News")
    WebElement news;
    @FindBy(partialLinkText = "Tasks")
    WebElement tasks;
    @FindBy(partialLinkText = "Records")
    WebElement records;
    @FindBy(partialLinkText = "Reports")
    WebElement reports;
    @FindBy(partialLinkText = "Actions")
    WebElement actions;


    @FindBy(partialLinkText = "HOME")
    WebElement linkHOME;
    @FindBy(partialLinkText = "MY ACCOUNT")
    WebElement linkMyAccount;

    @FindBy(css = ".appian-menu-item.appian-menu-item-selected")
    WebElement currentSelection;


    @Autowired
    public MainNavigationBar(WebDriver driver) {
        super(driver);
    }

    //==========================================================
    //
    // BUSINESS NAVIGATION BAR
    //
    //==========================================================


    public NewsTabPage clickNews() {
        WaitUtils.waitForElementToBeClickable(driver, news, TIMEOUT_DEFAULT, false);
        PageUtils.doubleClick(driver, news);
        return new NewsTabPage(driver);
    }

    public TasksTabPage clickTasks() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tasks, TIMEOUT_DEFAULT, false);
        tasks.click();
        PageUtils.acceptAlert(driver, true, 2);
        //PageUtils.doubleClick(driver, tasks);
        return new TasksTabPage(driver);
    }

    public RecordsTabPage clickRecords() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Records"), TIMEOUT_1_SECOND, 3);
        WaitUtils.waitForElementToBeClickable(driver, records, TIMEOUT_20_SECOND, false);
        //records.click();
        PageUtils.doubleClick(driver, records);
        return new RecordsTabPage(driver);
    }

    public ReportsTabPage clickReports() {
        WaitUtils.waitForElementToBeClickable(driver, reports, TIMEOUT_DEFAULT, false);
        reports.click();
        PageUtils.doubleClick(driver, reports);
        return new ReportsTabPage(driver);
    }

    public String getCurrentSelectedMenu() {
        WaitUtils.waitForElementToBeClickable(driver, currentSelection, TIMEOUT_DEFAULT, false);
        String selectedMenu = currentSelection.getText();
        return selectedMenu;
    }

    public ActionsTabPage clickActions() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, actions, TIMEOUT_DEFAULT, false);
        actions.click();
        PageUtils.doubleClick(driver, actions);
        return new ActionsTabPage(driver);
    }

    public boolean isCorrectPage(String expectedHeading) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        String title = getTitle();
        boolean isCorrectPage = title.contains(expectedHeading);
        return isCorrectPage;
    }




    //==========================================================
    //
    // MUNUFACTURER AND AUTHORISEDREP NAVIGATION BAR
    //
    //==========================================================


    public ExternalHomePage clickExternalHOME() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkHOME, TIMEOUT_DEFAULT, false);
            PageUtils.doubleClick(driver, linkHOME);
        }catch (Exception e){
            By xp = By.partialLinkText("home");
            WaitUtils.waitForElementToBeClickable(driver, xp, TIMEOUT_DEFAULT, false);
            PageUtils.doubleClick(driver, driver.findElement(xp));
        }
        PageUtils.acceptAlert(driver, "accept", 1);
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        return new ExternalHomePage(driver);
    }

    public MyAccountPage clickMyAccount() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkMyAccount, TIMEOUT_DEFAULT,  false);
            PageUtils.doubleClick(driver, linkMyAccount);
        }catch (Exception e){
            By xp = By.partialLinkText("my account");
            WaitUtils.waitForElementToBeClickable(driver, xp, TIMEOUT_DEFAULT, false);
            PageUtils.doubleClick(driver, driver.findElement(xp));
        }
        return new MyAccountPage(driver);
    }
}
