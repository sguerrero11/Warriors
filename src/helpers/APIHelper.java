package helpers;

import com.github.javafaker.Faker;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class APIHelper {

    /**
     * You need to instantiate each object in order to modify them, if not it returns null pointer.
     * bodyApiClients is for API registration (to get the token)
     * bodyPostOrder is to create new orders
     * Faker will create random data
     * accessToken is required for auth
     */
    private static JSONObject bodyApiClients = new JSONObject();
    private static JSONObject bodyPostOrder = new JSONObject();
    Faker faker = new Faker(new Locale("en"));
    public String accessToken;

    public void setBaseURI (String url) {
            RestAssured.baseURI = url;
    }

    public void postRegistration(){

//        bodyApiClients = new JSONObject();
        Faker faker = new Faker(new Locale("en"));

        bodyApiClients.put("clientName", faker.name().firstName());
        bodyApiClients.put("clientEmail", faker.name().firstName() + "@" + faker.color().name() + ".com" );

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

    public void getReq (String path, String paramKey, String paramValue){

        given()
                .basePath(path)
                .contentType(ContentType.JSON) //  .header("Content-type", "application/json")
                .queryParam(paramKey,paramValue)
                .log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(paramKey + "[0]", equalTo(paramValue))
                .log().all();
    }

    public void getSeveralCategories (String path, List categories){

        // arrange

        RequestSpecification query = given()
                .basePath(path)
                .contentType(ContentType.JSON); //  .header("Content-type", "application/json") ;



        categories.forEach(valueForCat-> {
            query.queryParam("category",valueForCat);
        });

        query.log().all();

        // act
        Response response = query.when()
                .get();

        // assert
        response.then()
                .statusCode(400)
              //  .body("category[0]", equalTo("categories[0]"))
                .log().all();

        /*
        given()
                .basePath(path)
                .contentType(ContentType.JSON) //  .header("Content-type", "application/json")
                .queryParam("category","ladders")
                .queryParam("category","plumbing")
                .log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("[0].category", equalTo(categories.get(0)))
                .log().all();

         */
    }

    public void getDifferentParams (String path, HashMap params) {

        // arrange

        RequestSpecification query = given()
                .basePath(path)
                .contentType(ContentType.JSON); //  .header("Content-type", "application/json") ;



        query.queryParams(params);


        query.log().all();

        // act
        Response response = query.when()
                .get();

        // assert
        response.then()
                .statusCode(200)
                .body("[0].category", equalTo(params.get("category") )) // [0].category es igual a category[0]
             //   .body("[0].category", equalTo("plumbing") )
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

    /**
     *
     * @param req --> the HTTP request to be performed
     * @param path --> the basePath for the request
     * @param qParams --> the queryParams for the GET, when applicable
     */
    public void sendRequest (String req, String path, HashMap pParams, HashMap qParams) {

        if (req=="del"){
            // arrange

            RequestSpecification query = given()
                    .auth().oauth2(accessToken)
                    .basePath(path)
                    .contentType(ContentType.JSON); //  .header("Content-type", "application/json") ;

            if (!(pParams.get("orderId")).equals(0)){
                query.pathParams(pParams);
            }

            query.log().all();

            // act

            if (!(pParams.get("orderId")).equals(0)) {
                Response response = query.when()
                        .delete("{orderId}");
                // assert
                response.then()
                        .statusCode(204)
                        .log().all();
            }



        }
        if (req=="post") {
            bodyPostOrder.put("toolId", "4643");
            bodyPostOrder.put("customerName", faker.name().firstName() + " " + faker.name().lastName());

            // arrange

            RequestSpecification query = given()
                    .auth().oauth2(accessToken)
                    .basePath(path)
                    .body(bodyPostOrder.toJSONString())
                    .contentType(ContentType.JSON); //  .header("Content-type", "application/json") ;


            query.log().all();

            // act
            Response response = query.when()
                    .post();

            // assert
            response.then()
                    .statusCode(201)
                    .log().all();


        }
        else if (req=="get"){
                // arrange

                RequestSpecification query = given()
                        .basePath(path)
                        .contentType(ContentType.JSON); //  .header("Content-type", "application/json") ;

                if (!(pParams.get("toolId")).equals(0)){
                    query.pathParams(pParams);
                }
                else if (!(qParams.get("category")).equals("")) {
                    query.queryParams(qParams);
                }


                query.log().all();

                // act
                if (!(pParams.get("toolId")).equals(0)) {
                    Response response = query.when()
                            .get("{toolId}");
                    // assert
                    response.then()
                            .statusCode(200)
                            .body("[0].id", equalTo(pParams.get("id") ))
                            .log().all();
                }
                else if (!(qParams.get("category")).equals("")) {
                // act
                    Response response = query.when()
                            .get();
                    // assert
                    response.then()
                            .statusCode(200)
                            .body("[0].category", equalTo(qParams.get("category") )) // [0].category es igual a category[0] // this body applies when there are qParams
                            .log().all();
                }

                else
                {
                    // act
                    Response response = query.when()
                            .get();
                    // assert
                    response.then()
                            .statusCode(200)
                         //   .body("[0].category", equalTo(qParams.get("category") )) // [0].category es igual a category[0] // this body applies when there are qParams
                            .log().all();
                }
            }
        }
}

