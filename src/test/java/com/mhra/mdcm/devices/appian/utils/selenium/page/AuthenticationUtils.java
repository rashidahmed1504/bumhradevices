package com.mhra.mdcm.devices.appian.utils.selenium.page;

import com.mhra.mdcm.devices.appian.utils.jenkins.ProxyAuthentication;
import com.mhra.mdcm.devices.appian.utils.jenkins.ProxyAuthenticationSikuli;
import org.openqa.selenium.WebDriver;

/**
 * Created by a-Uddinn on 4/11/2017.
 */
public class AuthenticationUtils {

    public static void performBasicAuthentication(WebDriver driver, String baseUrl) {
        String browser = System.getProperty("current.browser");
        String iSremote = System.getProperty("is.remote");
        if(iSremote != null && browser!=null && browser.toLowerCase().equals("gc") && iSremote.equals("true")) {
            //Only required if behind a proxy : works for Chrome
            driver.get(baseUrl);
            try {
                new ProxyAuthenticationSikuli(driver, baseUrl).login(false);
            } catch (Exception e) {
                try {
                    //Try again
                    new ProxyAuthenticationSikuli(driver, baseUrl).login(false);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
