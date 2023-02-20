package com.mock.messages;

import com.mock.common.ErrorCodes;
import com.mock.common.Helper;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseMessages {

    /**
     * validate request parameters
     *
     * @param type
     * @param reqJson
     * @param valParams
     * @return error message
     */
    public String validateRequest(String type, JsonObject reqJson, String valParams){
        List<String> paramList = new ArrayList<>();
        if(valParams.contains(",")) {
            paramList = Arrays.asList((valParams.split(",")));
        } else {
            paramList.add(valParams);
        }
        Helper helper = new Helper();
        if(type.equals("MANDATORY")) {
            String mandatoryStr = helper.validateMandatoryParameter(reqJson, paramList);
            if (!mandatoryStr.isEmpty()) {
                return errorMessageBuilder(type, mandatoryStr);
            }
        }
        if(type.equals("INVALID")) {
            String invalidParams = helper.validateInvalidParameter(reqJson, paramList);
            if (!invalidParams.isEmpty()) {
                return errorMessageBuilder(type, invalidParams);
            }
        }
        return null;
    }

    /**
     *
     * @param type
     * @param values
     * @return
     */
    private String errorMessageBuilder(String type, String values ){
        switch (type){
            case "MANDATORY":
                return " {\n" +
                        "   \"errorMessage\":\"missing mandatory parameter: "+values+"\",\n" +
                        "   \"accountStatus\":\"ERROR\",\n" +
                        "   \"errorCode\": \""+ ErrorCodes.USER_MANDATORY+"\"\n" +
                        "}";
            case "INVALID":
                return " {\n" +
                        "   \"errorMessage\":\"invalid input type: "+values+"\",\n" +
                        "   \"accountStatus\":\"ERROR\",\n" +
                        "   \"errorCode\": \""+ErrorCodes.USER_INVALID+"\"\n" +
                        "}";

        }
        return null;
    }
}
