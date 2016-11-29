package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountManufacturerRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;

import java.util.Map;

/**
 * Created by TPD_Auto on 14/07/2016.
 */
public class TestHarnessUtils {

    /**
     * Add specific data we want to AccountRequest object
     *
     * So that we can override default data
     *
     * @param dataSets
     * @return
     */
    public static AccountRequest updateBusinessDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        AccountRequest defaultAccount = new AccountRequest(scenarioSession);

        if(dataSets!=null){
            String accountType = dataSets.get("accountType");
            String countryName = dataSets.get("countryName");

            if(isNotEmptyOrNull(accountType)){
                if(accountType.contains("manufacturer")){
                    defaultAccount.isManufacturer = true;
                }else{
                    defaultAccount.isManufacturer = false;
                }
                defaultAccount.updateName(scenarioSession);
            }
            if(isNotEmptyOrNull(countryName)){
                defaultAccount.country = countryName;
            }
        }

        return defaultAccount;
    }

    private static boolean isNotEmptyOrNull(String data) {
        boolean isValid = true;

        if(data == null || data.trim().equals("")){
            isValid = false;
        }

        return isValid;
    }

    public static AccountManufacturerRequest updateManufacturerDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        AccountManufacturerRequest defaultAccount = new AccountManufacturerRequest(scenarioSession);

        if(dataSets!=null){
            String accountType = dataSets.get("accountType");
            String countryName = dataSets.get("countryName");

            if(isNotEmptyOrNull(accountType)){
                if(accountType.contains("manufacturer")){
                    defaultAccount.isManufacturer = true;
                }else{
                    defaultAccount.isManufacturer = false;
                }
                defaultAccount.updateName(scenarioSession);
            }
            if(isNotEmptyOrNull(countryName)){
                defaultAccount.country = countryName;
            }
        }

        return defaultAccount;
    }


    public static DeviceData updateDeviceData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        DeviceData dd = new DeviceData(scenarioSession);

        if(dataSets!=null){

            String deviceType = dataSets.get("deviceType");
            String gmdnDefinition = dataSets.get("gmdnDefinition");
            String gmdnCode = dataSets.get("gmdnCode");
            String customMade = dataSets.get("customMade");
            String relatedDeviceSterile = dataSets.get("relatedDeviceSterile");
            String relatedDeviceMeasuring = dataSets.get("relatedDeviceMeasuring");
            String riskClassification = dataSets.get("riskClassification");
            String notifiedBody = dataSets.get("notifiedBody");
            String packIncorporated = dataSets.get("packIncorporated");
            String devicesCompatible = dataSets.get("devicesCompatible");

            String productName = dataSets.get("productName");
            String productMake = dataSets.get("productMake");
            String productModel = dataSets.get("productModel");
            String subjectToPerfEval = dataSets.get("subjectToPerfEval");
            String newProduct = dataSets.get("newProduct");
            String conformsToCTS = dataSets.get("conformsToCTS");

            if(isNotEmptyOrNull(deviceType)){
                dd.deviceType = deviceType;
            }
            if(isNotEmptyOrNull(gmdnDefinition)){
                dd.gmdnTermOrDefinition = gmdnDefinition;
            }
            if(isNotEmptyOrNull(gmdnCode)){
                dd.gmdnCode = gmdnCode;
            }
            if(isNotEmptyOrNull(customMade)){
                dd.isCustomMade = Boolean.parseBoolean(customMade);
            }
            if(isNotEmptyOrNull(relatedDeviceSterile)){
                dd.isDeviceSterile = Boolean.parseBoolean(relatedDeviceSterile);
            }
            if(isNotEmptyOrNull(relatedDeviceMeasuring)){
                dd.isDeviceMeasuring = Boolean.parseBoolean(relatedDeviceMeasuring);
            }
            if(isNotEmptyOrNull(riskClassification)){
                dd.riskClassification = riskClassification;
            }
            if(isNotEmptyOrNull(notifiedBody)){
                dd.notifiedBody = notifiedBody;
            }

            if(isNotEmptyOrNull(packIncorporated)){
                dd.isPackIncorporated = Boolean.parseBoolean(packIncorporated);
            }
            if(isNotEmptyOrNull(devicesCompatible)){
                dd.isDeviceCompatible = Boolean.parseBoolean(devicesCompatible);
            }

            //IVD risk classification
            if(isNotEmptyOrNull(productName)){
                dd.productName = productName;
            }
            if(isNotEmptyOrNull(productMake)){
                dd.productMake = productMake;
            }
            if(isNotEmptyOrNull(productModel)){
                dd.productModel = productModel;
            }
            if(isNotEmptyOrNull(subjectToPerfEval)){
                dd.isSubjectToPerfEval = Boolean.parseBoolean(subjectToPerfEval);
            }
            if(isNotEmptyOrNull(newProduct)){
                dd.isNewProduct = Boolean.parseBoolean(newProduct);
            }
            if(isNotEmptyOrNull(conformsToCTS)){
                dd.isConformsToCTS = Boolean.parseBoolean(conformsToCTS);
            }

        }
        return dd;
    }
}
