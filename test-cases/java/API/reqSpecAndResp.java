package API;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import java.util.Locale;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

/**
 * This file shows different ways of using Rest Assured
 */

@Listeners({ProjectListener.class})
public class reqSpecAndResp {

    private static JSONObject bodyApiClients;
    private static JSONObject bodyPostOrder;
    public String bearerToken;
    Faker faker = new Faker(new Locale("en"));

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://simple-tool-rental-api.glitch.me";
    }

    // region Response
    @Test(enabled=true, description = "Get all the tools available", priority=100)
    public void getRequestAllTools() {

        given()
                .contentType(ContentType.JSON) //  .header("Content-type", "application/json")
                .queryParam("category","ladders")
                .log().all()
                .when()
                // .get("/tools?category=ladders") //we can pass url like this or with query params
                .get("/tools")
                .then()
                .statusCode(200)
                .body("category[0]", equalTo("ladders"))
                .log().all();
    }

    @Test (enabled=true, description = "To get the token", priority=300)
    public void registerApi() {
        bodyApiClients = new JSONObject();

        bodyApiClients.put("clientName", faker.name().firstName());
        bodyApiClients.put("clientEmail", faker.internet().emailAddress() );

        Response response = given()
                .body(bodyApiClients.toJSONString())
                .and()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post("/api-clients")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();

        bearerToken = response.jsonPath().getString("accessToken");

        System.out.println("Token: " + bearerToken);
    }

    @Test (enabled=true, description = "Create an order via POST", priority=500, dependsOnMethods = "registerApi")
    public void postOrder() {
        bodyPostOrder = new JSONObject();

        bodyPostOrder.put("toolId", "4643");
        bodyPostOrder.put("customerName", faker.name().firstName() + " " + faker.name().lastName());

        given()
                //.auth().preemptive().basic("required_username", "required_password") // for reference
                .auth().oauth2(bearerToken) // same as using header below
                //    .header("Authorization","Bearer " + bearerToken)
                //    .header("Authorization",bearerToken) //same as using with Bearer
                .body(bodyPostOrder.toJSONString())
                .and()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post("orders")
                .then()
                .statusCode(201)
                .log().all();
    }

    String id = "2177";

    @Test(description = "Get order by ID", priority=900)
    public void getRequestOrderById() {

        given()
                .basePath("tools")
                .pathParam("toolid",id)
                .contentType(ContentType.JSON) //  .header("Content-type", "application/json")
                .log().all()
                .when()
                .get("{toolid}") // when using pathParam and basePath --> best since we only replace string in path param with refactor
                //  .get ("/tools/" + id) // inline without using basePath and pathParam
                //  .get(id) // if we use basePath and no path params
                //  .get("/{toolid}",id) // inline param with basePath and overriding pathParam
                .then()
                .statusCode(200)
                //  .and()
                //    .body("id[0]", equalTo(id))
                .log().all();
    }

    // endregion

    // region Request Specification and Response Specification
    @Test (description = "Using Request Spec and Response Spec instead of Response", priority = 1000)
    public void requestSpecAndResp() {
        // Define the base URL for the API

        String path = "tools";
        // Tool ID for the request
        HashMap<String, Integer> pParams = new HashMap<>();
        HashMap<String, Boolean> qParams = new HashMap<>();
        pParams.put("toolId",2177);
        qParams.put("user-manual",true);

        // Define Request Specification
        RequestSpecification requestSpec = given()
                .basePath(path)
                // you could use both queryParams or params, they are the same
//                .queryParams(qParams)
//                .params(qParams)
//                .pathParams(pParams) // this translates to url/:toolId
                // Note: when used after the given, they apply for all subsequent requests
                .contentType(ContentType.JSON)
                .log().all();
        // Define Response Specification
        ResponseSpecification responseSpec = expect()
                .contentType(ContentType.JSON) // Assuming the response will be in JSON format
                .statusCode(200) // Expecting a successful response with status code 200
                .body("category", equalTo("ladders")) // Assuming category in the first node is ladders
                .body("manufacturer", equalTo("CoscoProducts")); // Assuming manufacturer in the first node is CoscoProducts
        // Send GET request and verify the response using specifications
        given()
                .spec(requestSpec) // Use Request Specification
            .when()
                .pathParams(pParams)
                .params(qParams) // used after the when applies only for this request
//                .queryParams(qParams) // both params or queryParams are valid
                .get("{toolId}")
            .then()
                .spec(responseSpec) // Use Response Specification
                .log().all();

        // another request sharing the same Request Specification (the given() portion)
        requestSpec
                .when()
                .get()
                .then()
                .log().body();

    }

    // endregion
}

