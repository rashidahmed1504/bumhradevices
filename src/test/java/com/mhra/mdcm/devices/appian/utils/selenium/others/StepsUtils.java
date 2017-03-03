package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.session.SessionKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto on 31/01/2017.
 */
public class StepsUtils {

    public static void addToDeviceDataList(ScenarioSession scenarioSession, DeviceData dd) {
        List<DeviceData> listOfDeviceData = (List<DeviceData>) scenarioSession.getData(SessionKey.deviceDataList);
        if(listOfDeviceData == null){
            listOfDeviceData = new ArrayList<>();
        }

        listOfDeviceData.add(dd);
        scenarioSession.putData(SessionKey.deviceDataList, listOfDeviceData);
    }


    public static void addToListOfStrings(ScenarioSession scenarioSession, String sessionKey, String value) {
        List<String> listOfStrings = (List<String>) scenarioSession.getData(sessionKey);
        if(listOfStrings == null){
            listOfStrings = new ArrayList<>();
        }

        listOfStrings.add(value);
        scenarioSession.putData(sessionKey, listOfStrings);
    }


    public static void addToListOfStrings(ScenarioSession scenarioSession, String sessionKey, List<String> values) {
        List<String> listOfStrings = (List<String>) scenarioSession.getData(sessionKey);
        if(listOfStrings == null){
            listOfStrings = new ArrayList<>();
        }

        for(String item: values) {
            listOfStrings.add(item);
        }

        scenarioSession.putData(sessionKey, listOfStrings);
    }

    public static String getCommaDelimitedData(List<String> listOfGmdns) {
        String commaDelimited = "";
        for(String x: listOfGmdns){
            if(x.contains(",")){
                commaDelimited = commaDelimited + "," + x.split(",")[0];
            }else{
                commaDelimited = commaDelimited + "," + x ;
            }
        }
        return commaDelimited;
    }
}
