package com.mock.services;

import com.mock.common.JsonConfReader;
import com.mock.messages.BaseMessages;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.UUID;

public class DepositUserService extends BaseService{

    public DepositUserService(Router router, JsonConfReader JsonConfReader){
        router.put("/exbank/api/deposit").produces("application/json").handler(routingContext -> {
            String jsnBody;
            JsonObject reqJson = routingContext.getBodyAsJson();
            BaseMessages mesgs = new BaseMessages();
            String jsonParams = JsonConfReader.getJsonConfObject().getServiceParams().get(1).getMandatoryParams();
            // request validate
            jsnBody = mesgs.validateRequest("MANDATORY", reqJson, jsonParams);
            if(jsnBody == null){
                jsnBody = mesgs.validateRequest("INVALID", reqJson, "amount");
            }
            String status = "DEPOSIT_ERROR";
            int code = 400;
            if( jsnBody == null){
                UUID uuid = UUID.randomUUID();
                // add account to the base map
                boolean isSuccess = addToAccountMap((String) reqJson.getValue("accountNumber"), reqJson.getDouble("amount"));
                if(isSuccess){
                    status = "DEPOSIT_SUCCESS";
                    code = 201;
                }
                jsnBody=" {\n" +
                        "   \"nameWithInitials\":\""+reqJson.getString("nameWithInitials")+"\",\n" +
                        "   \"transactionStatus\":\""+status+"\",\n" +
                        "   \"accountNumber\":\""+reqJson.getString("accountNumber")+"\",\n" +
                        "   \"transactionId\": \""+uuid+"\"\n" +
                        "}\n";

            }
            HttpServerResponse response = routingContext.response();
            JsonObject json = new JsonObject(jsnBody);
            response.putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(code)
                    .end(Json.encodePrettily(json));

        });
    }
}
