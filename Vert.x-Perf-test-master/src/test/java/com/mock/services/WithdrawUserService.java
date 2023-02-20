package com.mock.services;

import com.mock.common.JsonConfReader;
import com.mock.messages.BaseMessages;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.UUID;

public class WithdrawUserService extends BaseService{
    public WithdrawUserService(Router router, JsonConfReader JsonConfReader){
        router.patch("/exbank/api/withdraw").produces("application/json").handler(routingContext -> {
            String jsnBody;
            JsonObject reqJson = routingContext.getBodyAsJson();
            BaseMessages mesgs = new BaseMessages();
            String jsonParams = JsonConfReader.getJsonConfObject().getServiceParams().get(2).getMandatoryParams();
            // request validate
            jsnBody = mesgs.validateRequest("MANDATORY", reqJson, jsonParams);
            if(jsnBody == null){
                jsnBody = mesgs.validateRequest("INVALID", reqJson, "accountType,amount");
            }
            String status = "WITHDRAW_ERROR";
            int code = 400;
            if( jsnBody == null){
                UUID uuid = UUID.randomUUID();
                // add account to the base map
                boolean isSuccess = pullFromAccount(reqJson.getString("accountNumber"), reqJson.getDouble("amount"));
                if(isSuccess){
                    status = "WITHDRAW_SUCCESS";
                    code = 200;
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
