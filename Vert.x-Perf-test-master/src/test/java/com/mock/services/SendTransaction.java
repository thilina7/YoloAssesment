package com.mock.services;

import com.mock.common.JsonConfReader;
import com.mock.messages.BaseMessages;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.UUID;

public class SendTransaction extends BaseService{
    public SendTransaction(Router router, JsonConfReader JsonConfReader){
        router.patch("/exbank/api/send").produces("application/json").handler(routingContext -> {
            String jsnBody;
            JsonObject reqJson = routingContext.getBodyAsJson();
            BaseMessages mesgs = new BaseMessages();
            String jsonParams = JsonConfReader.getJsonConfObject().getServiceParams().get(3).getMandatoryParams();
            // request validate
            jsnBody = mesgs.validateRequest("MANDATORY", reqJson, jsonParams);
            if(jsnBody == null){
                jsnBody = mesgs.validateRequest("INVALID", reqJson, "amount");
            }
            String status = "SEND_ERROR";
            int code = 400;
            if( jsnBody == null){
                UUID uuid = UUID.randomUUID();
                // remove from account in the base map
                boolean isSuccess = pullFromAccount(reqJson.getString("accountNumber"), reqJson.getDouble("amount"));
                if(isSuccess){
                   status = "SEND_SUCCESS";
                    code = 201;
                }
                jsnBody=" {\n" +
                        "   \"receiverName\":\""+reqJson.getString("receiverName")+"\",\n" +
                        "   \"transactionStatus\":\""+status+"\",\n" +
                        "   \"receiverAccountNumber\":\""+reqJson.getString("receiverAccountNumber")+"\",\n" +
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
