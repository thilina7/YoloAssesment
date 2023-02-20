package com.exbank.tests;

import com.exbank.data.DataManager;
import com.exbank.listeners.TestListener;
import com.exbank.pojo.IntegrationDataObj;
import com.exbank.pojo.ServiceDataObj;
import com.exbank.services.BaseService;
import com.exbank.utils.FrameworkAnnotation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class IntegrationTest extends BaseService {

    BaseService service = new BaseService();
    private String accountNumber;

    @FrameworkAnnotation
    @Test(dataProvider = "getIntegrationData", dataProviderClass = DataManager.class, description = "end to end integration test journey: FT20001")
    public void endToEndJourney(IntegrationDataObj dataObj) throws JSONException, JsonProcessingException {
        // create user service
        Response createUserResponse = service.setCreateUserRequestObject(dataObj.getCreateReq());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(createUserResponse.getBody().prettyPrint());
        accountNumber = node.get("accountNumber").asText();

        // assert the response validation
        Assert.assertEquals(createUserResponse.getStatusCode(), 200, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getCreateRes(), createUserResponse.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("accountNumber", (o1, o2) -> true)
                ));


        // get balance service
        Response getBalanceRes = service.setBalanceRequestObject(accountNumber);
        mapper = new ObjectMapper();
        node = mapper.readTree(getBalanceRes.getBody().prettyPrint());
        double amount = node.get("amount").asDouble();
        //assert account balance amount
        Assert.assertEquals(amount, 0.0, "Error in amount validation");

        // deposit service
        String depositReq = dataObj.getDebitReq();
        node = mapper.readTree(depositReq);
        ((ObjectNode) node).put("accountNumber", accountNumber);

        // send the test data to service
        Response depositResObj = service.setDepositRequestObject(node.toPrettyString());

        String depositDataRes = dataObj.getDebitRes();
        node = mapper.readTree(depositDataRes);
        ((ObjectNode) node).put("accountNumber", accountNumber);
        // assert the response validation
        Assert.assertEquals(depositResObj.getStatusCode(), 201, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", node.toPrettyString(), depositResObj.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("transactionId", (o1, o2) -> true)
                ));

        // get balance service
        getBalanceRes = service.setBalanceRequestObject(accountNumber);
        mapper = new ObjectMapper();
        node = mapper.readTree(getBalanceRes.getBody().prettyPrint());
        amount = node.get("amount").asDouble();
        //assert account balance amount
        Assert.assertEquals(amount, 1000.50, "Error in amount validation");

        // withdraw service
        String withdrawReq = dataObj.getWithdraw_req();
        node = mapper.readTree(withdrawReq);
        ((ObjectNode) node).put("accountNumber", accountNumber);

        // withdraw test data to service
        Response withdrawResObj = service.setWithdrawRequestObject(node.toPrettyString());

        String withdrawRes = dataObj.getWithdraw_res();
        node = mapper.readTree(withdrawRes);
        ((ObjectNode) node).put("accountNumber", accountNumber);
        // assert the response validation
        Assert.assertEquals(withdrawResObj.getStatusCode(), 200, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", node.toPrettyString(), withdrawResObj.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("transactionId", (o1, o2) -> true)
                ));

        // get balance service
        getBalanceRes = service.setBalanceRequestObject(accountNumber);
        mapper = new ObjectMapper();
        node = mapper.readTree(getBalanceRes.getBody().prettyPrint());
        amount = node.get("amount").asDouble();
        //assert account balance amount
        Assert.assertEquals(amount, 800.25, "Error in amount validation");

        // send service
        String sendReq = dataObj.getSedReq();
        node = mapper.readTree(sendReq);
        ((ObjectNode) node).put("accountNumber", accountNumber);

        // send the test data to service
        Response sendResObj = service.setSendRequestObject(node.toPrettyString());

        String sendResObjDataRes = dataObj.getSendRes();

        // assert the response validation
        Assert.assertEquals(sendResObj.getStatusCode(), 201, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", sendResObjDataRes, sendResObj.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("transactionId", (o1, o2) -> true)
                ));

        // get balance service
        getBalanceRes = service.setBalanceRequestObject(accountNumber);
        mapper = new ObjectMapper();
        node = mapper.readTree(getBalanceRes.getBody().prettyPrint());
        amount = node.get("amount").asDouble();
        //assert account balance amount
        Assert.assertEquals(amount, 699.66, "Error in amount validation");
    }
}
