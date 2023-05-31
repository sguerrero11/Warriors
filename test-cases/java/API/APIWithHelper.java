package API;

import helpers.APIHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public abstract class APIWithHelper {

    private APIHelper api;

    @BeforeClass
    public void beforeTests() {
        api = new APIHelper();
        api.setBaseURI("https://simple-tool-rental-api.glitch.me"); // sets the endpoint
        api.postRegistration(); // creates token and thus sets Auth value
    }


    @Test(enabled=true, description = "Get all the tools", priority=200)
    public void getAllTools() {

        api.getReq("/tools","",""); // GET request with basePath and none queryParam
    }

    @Test(enabled=false, description = "Get all the tools in ladders", priority=300)
    public void getSpecificTools() {

/*
      String path, qParam1, qParam2;
      path = "/tools";
      qParam1 = "category";
      qParam2 = "ladders";
      api.getReq(path,qParam1, qParam2);
 */
      api.getReq("/tools","category","ladders"); // GET request with basePath and queryParam
    }

    @Test(enabled=true, description = "Post an order", priority=500)
    public void postOrder() {
        api.postOrder("orders");
    }
}
