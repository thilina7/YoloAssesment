package com.mock.common;


import io.vertx.core.json.JsonObject;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Helper implements ErrorCodes{

    /**
     * Mandatory parameters valdate in json request validate
     * @param requestJsObj
     * @param mandatoryParams
     * @return missing mandatory parameters
     */
    public String validateMandatoryParameter(JsonObject requestJsObj, List<String> mandatoryParams){
        String mandatory="";
        for (String key: mandatoryParams) {
            Object obj = requestJsObj.getValue(key);
            if (obj == null) {
                mandatory = stringAppend(mandatory, key);
            } else {
                if (obj instanceof String && String.valueOf(key).isEmpty()) {
                    mandatory = stringAppend(mandatory, key);
                } else if (obj instanceof Double && (Double) obj == 0) {
                    mandatory = stringAppend(mandatory, key);
                }
            }
        }
        return mandatory;
    }

    /**
     * Invalid parameters validate in json request validate
     * @param requestJsObj
     * @param invalidParams
     * @return invalid parameters
     */
    public String validateInvalidParameter(JsonObject requestJsObj, List<String> invalidParams){
        String invalidParam="";
        for (String key: invalidParams) {
            Object obj = requestJsObj.getValue(key);
            double amount= 0.0;
            if(obj instanceof String && key.equals("accountType")){
                String accountType = (String) requestJsObj.getValue("accountType");
                if(!accountType.equals("SAVING") && !accountType.equals("CREDIT") && !accountType.equals("COPARATE")){
                    invalidParam = stringAppend(invalidParam, key);
                }
            } else if (obj instanceof Double && key.equals("amount")) {
                amount = (Double) requestJsObj.getValue("amount");
                if(amount <= 0.0) {
                    invalidParam = stringAppend(invalidParam, key);
                }
            }
        }
        return invalidParam;
    }

    /**
     * append parameters as String
     * @param exist
     * @param appender
     * @return parameters
     */
    private String stringAppend(String exist, String appender){
        if(exist.isEmpty()){
            return appender;
        } else {
            return exist+", "+appender;
        }
    }
}
