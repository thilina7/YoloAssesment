package com.exbank.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RequestObj {

    private String serviceName;
    private String url;
    private String serviceType;
    private String jsonBody;
    private Map<String, String> headers = new TreeMap<>();

}
