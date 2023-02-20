package com.exbank.tests;

import com.exbank.data.DataManager;
import com.exbank.pojo.ServiceDataObj;
import com.exbank.services.BaseService;
import com.exbank.utils.FrameworkAnnotation;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners
public class GetBalanceTest extends BaseService {

    BaseService service = new BaseService();
    @FrameworkAnnotation
    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "get balance without accountNumber param: FT0050")
    public void sendServiceWithoutAccountNumberparam(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setBalanceRequestObject("invalidParam", dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),404, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("accountNumber", (o1, o2) -> true)
                ));
    }

    @FrameworkAnnotation
    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "get balance with incorrect accountNumber param: FT0050")
    public void sendServiceWithInvalidAccountNumber(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setBalanceRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("accountNumber", (o1, o2) -> true)
                ));
    }
}
