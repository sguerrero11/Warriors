package API;

import helpers.APIHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class APIWithHelper {

    private APIHelper api;

    @BeforeClass
    public void beforeTests() {
        api = new APIHelper();
        api.setBaseURI("https://simple-tool-rental-api.glitch.me"); // sets the endpoint
        api.postRegistration(); // creates token and thus sets Auth value
    }

    // region DISTRIBUTED TESTS

    @Test(enabled=true, description = "Get all the tools", priority=200)
    public void getAllTools() {

        api.getReq("/tools","",""); // GET request with basePath and none queryParam
    }

    @Test(enabled=true, description = "Get all the ladders", priority=300)
    public void getAllLadders() {

        api.getReq("/tools","category","ladders"); // GET request with basePath and queryParam
    }

    @Test(enabled=true, description = "Get more than one category", priority=400)
    public void getMultipleCategories() {

        List<String> categories = new ArrayList<>();
        categories.add("ladders");
        categories.add("plumbing");


        api.getSeveralCategories("/tools",categories);
    }

    @Test(enabled=true, description = "Get more than one param", priority=500)
    public void getSeveralParams() {

        HashMap <String, String> params = new HashMap<>();
        params.put("category", "ladders");
        params.put("results", "1");
        System.out.println("HashMap values: " + params);

        api.getDifferentParams("/tools",params);
    }


    @Test(enabled=false, description = "Get all the tools in ladders", priority=600)
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

    @Test(enabled=true, description = "Post an order", priority=700)
    public void postOrder() {
        api.postOrder("orders"); // POST request under orders path
    }

    // endregion

    // region UNIFIED TEST

    @Test(enabled=true, description = "Send any request", priority=700)
    public void sendRequest(){

        /**
         * Available paths are: tools, orders
         * Available categories for qParams are: ladders, plumbing, power-tools, electric-generators, trailers
         * Available keys for qParams are: category, results (amount of results), available (true/false), user-manual (true/false); for orders --> invoice (true/false)
         * Available keys for pParams are (key name could be anything): for tools --> toolId (id for the tool); for orders --> orderId (id for the order)
         */

        // region ENTER HERE TO DEFINE HTTP REQUEST

        String req = "get";

        // endregion

        // region ENTER HERE TO DEFINE PATH
        String path = "tools";
        // endregion

        // region ENTER HERE TO DEFINE PATH PARAMS
        HashMap <String, Integer> pParams = new HashMap<>();
//        pParams.put("toolId",2177);
        pParams.put("toolId",0); // use this line when you don't want pParams

        // endregion

        // region ENTER HERE TO DEFINE QUERY PARAMS
        HashMap <String, String> qParams = new HashMap<>();
        qParams.put("category", "ladders");
        qParams.put("results", "1");
//        qParams.put("category",""); // use this line when you don't want qParams
        // endregion

        api.sendRequest(req,path,pParams,qParams); // method that leverages on API Helper
    }

    // endregion
}
