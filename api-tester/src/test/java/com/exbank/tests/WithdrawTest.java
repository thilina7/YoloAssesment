package com.exbank.tests;

import com.exbank.data.DataManager;
import com.exbank.pojo.ServiceDataObj;
import com.exbank.services.BaseService;
import com.exbank.utils.FrameworkAnnotation;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners
public class WithdrawTest extends BaseService {

    BaseService service = new BaseService();

    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "withdraw Invoke Without account number and amount: FT0036")
    @FrameworkAnnotation
    public void withdrawUserInvokeWithoutAccountNumberAndName(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setWithdrawRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(), JSONCompareMode.STRICT);
    }

    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "withdraw Invoke Without account number: FT0029")
    @FrameworkAnnotation
    public void withdrawServiceWithoutAccountNumber(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setWithdrawRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(), JSONCompareMode.STRICT);
    }
}
