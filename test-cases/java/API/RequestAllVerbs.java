package API;


import com.fasterxml.jackson.core.JsonpCharacterEscapes;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestAllVerbs {

    private static JSONObject bodyApiClients;
    private static JSONObject bodyPostOrder;
    public String bearerToken;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://simple-tool-rental-api.glitch.me";
    }

    @Test(enabled=true, description = "Get all the tools available for category ladders", priority=100)
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
        Faker faker = new Faker(new Locale("en"));


        bodyApiClients.put("clientName", faker.name().firstName());
        bodyApiClients.put("clientEmail", faker.internet().emailAddress());

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
        Faker faker = new Faker(new Locale("en"));

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

}
