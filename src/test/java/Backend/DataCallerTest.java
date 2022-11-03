package Backend;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.vaadin.covid.backend.DataCaller;
import org.vaadin.covid.backend.RequestedURLEnum;
import org.vaadin.covid.jpa.Status;

import java.io.IOException;

public class DataCallerTest {
    DataCaller dataCaller;
    @Test
    public void testDataCaller() throws JSONException, IOException {
        try{
            dataCaller = new DataCaller(null);
            Assert.fail();
        }catch (Exception e){
            Assert.assertEquals("RequestedURL cannot be 'null'", e.getMessage());
        }

        dataCaller = new DataCaller(RequestedURLEnum.status);
        Assert.assertTrue(dataCaller.callData() instanceof Status);
    }
}
