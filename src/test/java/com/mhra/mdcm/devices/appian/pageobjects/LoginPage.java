package com.mhra.mdcm.devices.appian.pageobjects;

import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AuthenticationUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Properties;

/**
 * @author TPD_Auto
 */
@Component
public class LoginPage extends _Page {

    private static String baseUrl;
    public static boolean isAutorised = false;

    //Login fields
    @FindBy(id = "un")
    WebElement username;
    @FindBy(id = "pw")
    WebElement password;
    @FindBy(css = "input#remember")
    WebElement remember;
    @FindBy(css = ".choice_pair>label")
    WebElement rememberLabel;

    //Settings icons and links
    @FindBy(css = ".gwt-Anchor.pull-down-toggle")
    WebElement settings;
    @FindBy(css = ".gwt-Anchor.pull-down-toggle span")
    WebElement loggedInUserBusiness;
    @FindBy(css = ".UserProfileLayout---current_user_menu_wrapper")
    WebElement loggedInUserManufacturer;
    @FindBy(xpath = ".//span[contains(@style, 'personalization')]")
    WebElement photoIcon;
    @FindBy(xpath = ".//*[contains(text(),'Sign Out')]")
    WebElement signOutLink;

    //Change password form
    @FindBy(name = "oldPw")
    WebElement passwordTemporary;
    @FindBy(name = "newPw")
    WebElement changePasswordNew;
    @FindBy(name = "confirmNewPw")
    WebElement changePasswordNewConfirm;

    //Forgotten password or change password
    @FindBy(id = "logo")
    WebElement mhraLogo;
    @FindBy(id = "forgotPasswordLink")
    WebElement linkForgottenPassword;
    @FindBy(id = "back_link")
    WebElement linkBackToSignInPage;
    @FindBy(name = "username")
    WebElement fpUserName;
    @FindBy(css = ".confirmValue")
    WebElement confirmUserName;
    @FindBy(name = "newPw")
    WebElement tbxNewPassword;
    @FindBy(name = "confirmNewPw")
    WebElement tbxConfirmNewPassword;

    //Terms and conditions
    @FindBy(xpath = ".//div[@class='button_box_buttons']//input")
    WebElement acceptTermsAndConditions;

    //Buttons
    @FindBy(xpath = ".//input[@value='Log in']")
    WebElement btnLogin;
    @FindBy(xpath = ".//input[@type='submit']")
    WebElement btnSubmit;


    //Error message
    @FindBy(xpath = ".//img[@id='logo']//following::div[@class='message']")
    WebElement errorMsg;

    @Autowired
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage loadPage(String url) {
        if(driver!=null && !isAutorised){
            isAutorised = true;
            AuthenticationUtils.performBasicAuthentication(driver, url);
        }
        PageUtils.acceptAlert(driver, true, 1);
        driver.get(url);
        PageUtils.acceptAlert(driver, "accept", 1);
        baseUrl = url;
        return new LoginPage(driver);
    }

    public MainNavigationBar login(String unameKeyValue) {
        //dontRemember(); removed 26/06/2017

        //System properties decides which users to use and which profile to use
        String selectedProfile = System.getProperty("spring.profiles.active");
        String overrideWithUsername = System.getProperty("test.as.user");

        //get login details from properties files
        Properties props = FileUtils.loadPropertiesFile(FileUtils.userFileName);

        //check if local properties supplies a user to override all tests with
        if(overrideWithUsername==null || overrideWithUsername.equals("")) {
            overrideWithUsername = props.getProperty("mhratest.user.default.override.username");
        }

        //Override local uname with overriden values
        unameKeyValue = FileUtils.getOverriddenUsername(overrideWithUsername, unameKeyValue);

        //Now load the correct username password
        String un = props.getProperty(selectedProfile + ".username." + unameKeyValue);
        String pword = props.getProperty(selectedProfile + ".password." + unameKeyValue);

        if(un == null || pword == null){
            props = FileUtils.loadPropertiesFile("data" + File.separator + "other.configs.properties");
            un = props.getProperty(selectedProfile + ".username." + unameKeyValue);
            pword = props.getProperty(selectedProfile + ".password." + unameKeyValue);
        }

        log.info("Login as : " + un + "/" + pword);

        //login
        username.sendKeys(un);
        password.sendKeys(pword);
        username.submit();

        return new MainNavigationBar(driver);
    }

    public MainNavigationBar loginWithSpecificUsernamePassword(String usernameTxt, String passwordTxt) {
        //dontRemember(); removed 26/06/2017

        //login
        WaitUtils.waitForElementToBeClickable(driver, username, TIMEOUT_10_SECOND);
        username.sendKeys(usernameTxt);
        password.sendKeys(passwordTxt);
        username.submit();

        return new MainNavigationBar(driver);
    }

    public MainNavigationBar loginDataDriver(String usernameTxt, String passwordTxt) {
        logoutIfLoggedIn();
        //dontRemember(); removed 26/06/2017

        //login
        WaitUtils.waitForElementToBeClickable(driver, username, TIMEOUT_10_SECOND);
        username.sendKeys(usernameTxt);
        password.sendKeys(passwordTxt);
        username.submit();

        return new MainNavigationBar(driver);
    }

    public MainNavigationBar reloginUsing(String uname) {
        logoutIfLoggedIn();
        MainNavigationBar login = login(uname);
        return login;
    }

    public void dontRemember() {
        WaitUtils.waitForElementToBeClickable(driver, remember, TIMEOUT_10_SECOND);
        if (remember.getAttribute("checked") != null) {
            //remember.click();
            rememberLabel.click();
        }
    }

    /**
     * Business site
     *
     * @return
     */
    public LoginPage logoutIfLoggedIn() {
        boolean loggedOut = isAlreadyLoggedOut();
        if(!loggedOut) {
            try {
                WaitUtils.waitForElementToBeClickable(driver, settings, TIMEOUT_10_SECOND);
                if (settings.isDisplayed()) {
                    //settings.click();
                    PageUtils.doubleClick(driver, settings);
                    driver.findElement(By.linkText("Sign Out")).click();

                    //If logout and login is too fast, appian system shows 404 in some instance of automation
                    WaitUtils.nativeWaitInSeconds(2);
                    driver.get(baseUrl);

                    WaitUtils.waitForElementToBeClickable(driver, linkForgottenPassword, TIMEOUT_10_SECOND);
                }
            } catch (Exception e) {
                //Probably not logged in
            }
        }
        return new LoginPage(driver);
    }

    private boolean isAlreadyLoggedOut() {
        boolean loggedOut = true;
        try{
            //WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("Forgot your password"), 2);
            WaitUtils.waitForElementToBeClickable(driver, acceptTermsAndConditions, 2);
        }catch (Exception e){
            loggedOut = false;
        }
        return loggedOut;
    }

    /**
     * logout from Manufacturer and AuthorisedRep site
     *
     * @return
     */
    public LoginPage logoutIfLoggedInOthers() {
        boolean loggedOut = isAlreadyLoggedOut();
        if(!loggedOut) {
            try {
                WaitUtils.waitForElementToBeClickable(driver, photoIcon, TIMEOUT_10_SECOND);
                if (photoIcon.isDisplayed()) {
                    //settings.click();
                    PageUtils.doubleClick(driver, photoIcon);
                    signOutLink.click();

                    //If logout and login is too fast, appian system shows 404 in some instance of automation
                    WaitUtils.nativeWaitInSeconds(2);
                    driver.get(baseUrl);

                    WaitUtils.waitForElementToBeClickable(driver, linkForgottenPassword, TIMEOUT_10_SECOND);
                    //If logout and login is too fast, appian system shows 404 in some instance of automation
                    WaitUtils.nativeWaitInSeconds(2);
                }
            } catch (Exception e) {
                //Probably not logged in
            }
        }
        return new LoginPage(driver);
    }

    public String getLoggedInUserName(String usernameKey) {
        if(usernameKey.contains("business")) {
            WaitUtils.waitForElementToBeClickable(driver, loggedInUserBusiness, TIMEOUT_1_SECOND);
            return loggedInUserBusiness.getAttribute("aria-label");
        }else{
            //Manufacturer or authorised reps
            WaitUtils.waitForElementToBeClickable(driver, loggedInUserManufacturer, 2);
            loggedInUserManufacturer.click();
            return driver.findElement(By.cssSelector(".UserProfileLayout---current_userid strong")).getText();
        }
    }

    public boolean isErrorMessageCorrect(String expectedErrorMsg) {
        WaitUtils.waitForElementToBeVisible(driver, errorMsg, TIMEOUT_10_SECOND);
        boolean contains = errorMsg.getText().contains(expectedErrorMsg);
        return contains;
    }

    public boolean isAlreadyLoggedInAsUser(String usernameKey) {
        boolean alreadyLoggedInAsUser = false;
        try {
            //Check if we are already logged in as the username
            Properties props = FileUtils.loadPropertiesFile(FileUtils.userFileName);
            String selectedProfile = System.getProperty("spring.profiles.active");
            String usernameExpected = props.getProperty(selectedProfile + ".username." + usernameKey).replaceAll("\\.", " ");;

            String loggedInUserName = getLoggedInUserName(usernameKey);
            if(loggedInUserName!=null && loggedInUserName.contains(usernameExpected)){
                alreadyLoggedInAsUser = true;
            }
        } catch (Exception e) {
            //Not logged in
            e.printStackTrace();
        }
        return alreadyLoggedInAsUser;
    }

    public boolean isInLoginPage(int timeout) {
        try {
            WaitUtils.waitForElementToBeClickable(driver, btnLogin, timeout);
            boolean isLoginPage = btnLogin.isDisplayed() && btnLogin.isEnabled();
            return isLoginPage;
        }catch (Exception e){
            return false;
        }
    }

    public MainNavigationBar changePasswordTo(String tempPassword, String updatePasswordTo) {
        WaitUtils.waitForElementToBeClickable(driver, btnSubmit, TIMEOUT_10_SECOND);
        passwordTemporary.sendKeys(tempPassword);
        changePasswordNew.sendKeys(updatePasswordTo);
        changePasswordNewConfirm.sendKeys(updatePasswordTo);
        btnSubmit.click();
        return new MainNavigationBar(driver);
    }

    public void logout(String currentLoggedInUser) {
        //Note page displayed to Business user is different from Manufacturer and AuthorisedRep
        if(currentLoggedInUser!=null){
            if(currentLoggedInUser.toLowerCase().contains("business")){
                logoutIfLoggedIn();
            }else if(currentLoggedInUser.toLowerCase().contains("manufacturer")){
                logoutIfLoggedInOthers();
            }else if(currentLoggedInUser.toLowerCase().contains("authorised")){
                logoutIfLoggedInOthers();
            }
        }
        PageUtils.acceptAlert(driver, true, 1);
    }

    public LoginPage gotoForgottenPassword() {
        WaitUtils.waitForElementToBeClickable(driver, linkForgottenPassword, TIMEOUT_10_SECOND);
        linkForgottenPassword.click();
        return new LoginPage(driver);
    }

    public LoginPage enterUsername(String userName) {
        WaitUtils.waitForElementToBeClickable(driver, fpUserName, TIMEOUT_10_SECOND);
        fpUserName.sendKeys(userName);
        fpUserName.submit();
        return new LoginPage(driver);
    }

    public boolean isChangePasswordUsernameCorrect(String userNameTxt) {
        WaitUtils.waitForElementToBeClickable(driver, confirmUserName, TIMEOUT_10_SECOND);
        return confirmUserName.getText().contains(userNameTxt);
    }

    public MainNavigationBar updateThePasswordTo(String newPassword) {
        WaitUtils.waitForElementToBeClickable(driver, tbxNewPassword, TIMEOUT_10_SECOND);
        tbxNewPassword.sendKeys(newPassword);
        tbxConfirmNewPassword.sendKeys(newPassword);
        tbxConfirmNewPassword.submit();
        return new MainNavigationBar(driver);
    }

    public LoginPage acceptTermsAndConditions() {
        if(isTermsAndConditionDisplayed(2)) {
            acceptTermsAndConditions.click();
        }
        return new LoginPage(driver);
    }

    public boolean isTermsAndConditionDisplayed(int timeout) {
        try {
            WaitUtils.waitForElementToBeClickable(driver, acceptTermsAndConditions, timeout);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
