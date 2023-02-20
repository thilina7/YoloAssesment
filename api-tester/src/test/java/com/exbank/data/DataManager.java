package com.exbank.data;

import com.exbank.pojo.IntegrationDataObj;
import com.exbank.pojo.ServiceDataObj;
import io.github.sskorol.core.DataSupplier;
import io.github.sskorol.data.TestDataReader;
import io.github.sskorol.data.XlsxReader;
import one.util.streamex.StreamEx;
import java.lang.reflect.Method;


/**
 * Use for the excel data manage
 */
public class DataManager {
    @DataSupplier(runInParallel = true)
    public StreamEx<ServiceDataObj> getServiceData(Method method) {
        return TestDataReader.use(XlsxReader.class)
                .withTarget(ServiceDataObj.class)
                .withSource("excel/testdata.xlsx")
                .read()
                .filter(createUserDataObj -> createUserDataObj.getTestCaseName().equalsIgnoreCase(method.getName()));
    }

    @DataSupplier(runInParallel = true)
    public StreamEx<IntegrationDataObj> getIntegrationData(Method method) {
        return TestDataReader.use(XlsxReader.class)
                .withTarget(IntegrationDataObj.class)
                .withSource("excel/integrationtestdata.xlsx")
                .read()
                .filter(integrationDataObj -> integrationDataObj.getTestCaseName().equalsIgnoreCase(method.getName()));
    }
}