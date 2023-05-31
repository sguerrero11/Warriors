package helpers;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class APIHelper {

    private static JSONObject bodyApiClients;
    private static JSONObject bodyPostOrder;
    Faker faker = new Faker(new Locale("en"));
    public String accessToken;


    public void setBaseURI (String url) {
            RestAssured.baseURI = url;
    }


    public void postRegistration(){

        bodyApiClients = new JSONObject();
        Faker faker = new Faker(new Locale("en"));

        bodyApiClients.put("clientName", faker.name().firstName());
        bodyApiClients.put("clientEmail", faker.name().firstName()+"@" + faker.color().name() + ".com" );

        Response response = given()
                .basePath("api-clients")
                .body(bodyApiClients.toJSONString())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        accessToken = response.jsonPath().getString("accessToken");
        System.out.println("Token: " + accessToken);

    }

    public void getReq (String path, String string, String object){

        given()
                .basePath(path)
                .contentType(ContentType.JSON) //  .header("Content-type", "application/json")
                .queryParam(string,object)
                .log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(string + "[0]", equalTo(object))
                .log().all();
    }

    public void postOrder (String path){

        bodyPostOrder.put("toolId", "4643");
        bodyPostOrder.put("customerName", faker.name().firstName() + " " + faker.name().lastName());

        given()
                .auth().oauth2(accessToken)
                .basePath(path)
                .body(bodyPostOrder.toJSONString())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post()
                .then()
                .statusCode(201)
                .log().all();
    }
}
