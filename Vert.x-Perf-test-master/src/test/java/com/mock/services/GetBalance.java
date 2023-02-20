package com.mock.services;

import com.mock.common.JsonConfReader;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class GetBalance extends BaseService{
    /**
     * get balance request
     * @param router
     */
    public GetBalance(Router router){
        router.get("/exbank/api/getbalance").produces("application/json").handler(routingContext -> {
            String jsnBody=null;
            String accountNumber="";
            double amount=0.00;
            int code=400;
            if((routingContext).queryParams().contains("accountNumber")){
                accountNumber = (routingContext).queryParams().get("accountNumber");
                if(!accountNumber.isEmpty()) {
                    if(isAccountAvailable(accountNumber)) {
                        amount = getAccountBalance(accountNumber);
                        code = 200;
                    }
                } else {
                    code=400;
                }
            }
            else {
                code=404;
            }

            if( !accountNumber.isEmpty() && isAccountAvailable(accountNumber)){
                jsnBody=" {\n" +
                        "\"accountNumber\": \""+accountNumber+"\",\n" +
                        "\"amount\": "+amount+"\n" +
                        "}";
            } else {
                jsnBody="  {\n" +
                        "   \"errorMessage\":\"invalid query param: accountNumber\",\n" +
                        "   \"accountStatus\":\"ERROR\",\n" +
                        "   \"errorCode\": \"ER34001\"\n" +
                        "}";
            }
            HttpServerResponse response = routingContext.response();

            JsonObject json = new JsonObject(jsnBody);
            response.putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(code)
                    .end(Json.encodePrettily(json));

        });
    }
}
