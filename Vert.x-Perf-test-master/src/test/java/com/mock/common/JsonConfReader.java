package com.mock.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mock.pojo.BackendConf;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ksrajith on 10/15/19.
 */
public class JsonConfReader {

    private BackendConf jsonConfObject = null;

    public JsonConfReader() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonConfObject = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream("config.json"), BackendConf.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BackendConf getJsonConfObject() {
        return jsonConfObject;
    }
}
