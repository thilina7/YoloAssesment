package com.mock.services;

import com.mock.common.JsonConfReader;
import com.mock.messages.BaseMessages;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import java.util.UUID;

public class CreateUserService extends BaseService{

    /**
     * create_user request
     * @param router
     * @param JsonConfReader
     */
    public CreateUserService(Router router, JsonConfReader JsonConfReader){
        router.post("/exbank/api/createUser").produces("application/json").handler(routingContext -> {
            String jsnBody;
            int code = 400;
            JsonObject reqJson = routingContext.getBodyAsJson();
            BaseMessages mesgs = new BaseMessages();
            String jsonParams = JsonConfReader.getJsonConfObject().getServiceParams().get(0).getMandatoryParams();
            // request validate
            jsnBody = mesgs.validateRequest("MANDATORY", reqJson, jsonParams);
            if(jsnBody == null){
                jsnBody = mesgs.validateRequest("INVALID", reqJson, "accountType");
            }

            if( jsnBody == null){
                UUID uuid = UUID.randomUUID();
                code=200;
                jsnBody="{\n" +
                        "   \"nameWithInitials\":\""+reqJson.getString("nameWithInitials")+"\",\n" +
                        "   \"accountStatus\":\"CREATED\",\n" +
                        "   \"accountNumber\":\"REB"+uuid+"\"\n" +
                        "}";
                // set account to the base map
                setAccountToMap("REB"+uuid);
            }
            HttpServerResponse response = routingContext.response();

            JsonObject json = new JsonObject(jsnBody);
            response.putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(code)
                    .end(Json.encodePrettily(json));

        });
    }

}
