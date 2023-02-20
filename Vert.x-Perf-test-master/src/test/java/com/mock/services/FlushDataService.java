package com.mock.services;

import com.mock.common.JsonConfReader;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class FlushDataService extends BaseService {

    /**
     * data flush request
     *
     * @param router
     */
    public FlushDataService(Router router) {
        router.get("/exbank/api/flushData").produces("application/json").handler(routingContext -> {
            // clear the data map
            dataFlush();
            String jsnBody = "{\"dataFlush\":\"Data_FLUSH_SUCESS\"}";

            HttpServerResponse response = routingContext.response();
            JsonObject json = new JsonObject(jsnBody);
            response.putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(200)
                    .end(Json.encodePrettily(json));

        });
    }
}
