package com.exbank.tests;

import com.exbank.base.BaseTest;
import com.exbank.listeners.TestListener;
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


@Listeners(TestListener.class)
public class CreateUserTest extends BaseTest {

    BaseService service = new BaseService();
    @FrameworkAnnotation
    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "create User Invoke With All CorrectData: FT0001")
    public void createUserInvokeWithAllCorrectData(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setCreateUserRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),200, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("accountNumber", (o1, o2) -> true)
                ));
    }

    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "create User Invoke Without AccountType: FT0012")
    @FrameworkAnnotation
    public void createUserInvokeWithoutAccountType(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setCreateUserRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(), JSONCompareMode.STRICT);
    }

    @Test(dataProvider = "getServiceData", dataProviderClass = DataManager.class, description = "create User Invoke With invalid AccountType: FT0011")
    @FrameworkAnnotation
    public void createUserInvokeWithInvalidAccountType(ServiceDataObj dataObj) throws JSONException {
        // send the test data to service
        Response response = service.setCreateUserRequestObject(dataObj.getRequest());
        // assert the response validation
        Assert.assertEquals(response.getStatusCode(),400, "Error in response code validation");
        JSONAssert.assertEquals("Error in json response validation", dataObj.getResponse(), response.getBody().prettyPrint(), JSONCompareMode.STRICT);
    }

}
