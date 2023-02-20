package com.exbank.tests;

import com.exbank.base.BaseTest;
import com.exbank.data.DataManager;
import com.exbank.listeners.TestListener;
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


@Listeners(TestListener.class)
public class DepositTest  extends BaseTest {

    BaseService service = new BaseService();

    @FrameworkAnnotation
    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "deposit service without nameWithInitials: FT0028")
    public void depositServiceWithoutNameWithInitials(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setDepositRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("accountNumber", (o1, o2) -> true)
                ));
    }

    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "deposit service without Source: FT0034")
    @FrameworkAnnotation
    public void depositServiceWithoutSource(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setDepositRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(), JSONCompareMode.STRICT);
    }

    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "deposit service with negative amount")
    @FrameworkAnnotation
    public void depositInvokeWithNegativeAmount(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setDepositRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation: FT0032");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(), JSONCompareMode.STRICT);
    }

}
