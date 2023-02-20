package com.exbank.services;

import com.exbank.config.ConfigFactory;
import com.exbank.pojo.ServiceDataObj;
import com.exbank.pojo.RequestObj;
import com.exbank.rest.RequestSender;
import io.restassured.response.Response;

public class BaseService {

    RequestObj requestObj = new RequestObj();
    RequestSender sender = new RequestSender();

    /**
     * Prepare create request object
     * @param requestJson
     * @return reponse from the server
     */
    public Response setCreateUserRequestObject(String requestJson){
        requestObj.setUrl(ConfigFactory.getConfig().url()+"createUser");
        requestObj.setJsonBody(requestJson);
        requestObj.setServiceType("POST");
        return sender.apiRequestSend(requestObj);
    }

    /**
     * Prepare deposit request object
     * @param request
     * @return reponse from the server
     */
    public Response setDepositRequestObject(String request){
        requestObj.setUrl(ConfigFactory.getConfig().url()+"deposit");
        requestObj.setJsonBody(request);
        requestObj.setServiceType("PUT");
        return sender.apiRequestSend(requestObj);
    }

    /**
     * Prepare deposit request object
     * @param request
     * @return reponse from the server
     */
    public Response setWithdrawRequestObject(String request){
        requestObj.setUrl(ConfigFactory.getConfig().url()+"withdraw");
        requestObj.setJsonBody(request);
        requestObj.setServiceType("PATCH");
        return sender.apiRequestSend(requestObj);
    }

    /**
     * Prepare get balance request
     * @param request
     * @return reponse from the server
     */
    public Response setBalanceRequestObject(String request){
        requestObj.setUrl(ConfigFactory.getConfig().url()+"getbalance?accountNumber="+request);
        requestObj.setServiceType("GET");
        return sender.apiRequestSend(requestObj);
    }

    /**
     * Prepare get balance request
     * @param param
     * @param request
     * @return reponse from the server
     */
    public Response setBalanceRequestObject(String param, String request){
        requestObj.setUrl(ConfigFactory.getConfig().url()+"getbalance?"+param+"="+request);
        requestObj.setServiceType("GET");
        return sender.apiRequestSend(requestObj);
    }

    /**
     * Prepare send request object
     * @param request
     * @return reponse from the server
     */
    public Response setSendRequestObject(String request){
        requestObj.setUrl(ConfigFactory.getConfig().url()+"send");
        requestObj.setJsonBody(request);
        requestObj.setServiceType("PATCH");
        return sender.apiRequestSend(requestObj);
    }

}
