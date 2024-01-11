package helpers;



//import api_services.GraphQLQuery;
//import com.google.api.client.auth.oauth.OAuthParameters;
//import com.google.api.client.http.GenericUrl;
//import config.Env;
//import helpers.Minify;
//import helpers.OAuthHmacSha256Signer;
//import helpers.LoggerHelper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

/***
 * Helper class to handle Api REST requests.
 */
public class ApiHelper {

    private static String epContext;

    public static void setEpContext(String context){
        epContext = context;
    }

    public static Response get(String uri) {
        RequestSpecification query = given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().
                appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        query.accept("*/*")
                .header("Content-Type", "application/json");

        Response response = query.when().get(uri);
        LoggerHelper.logInfo("[ApiHelper/post]: Response log");
        response.then().log().body();

        return response;
    }

    public static Response getWithBody(String uri, HashMap<String, Object> params, String token)
    {
        params.put("hasBody", true);
        return get(uri, params, token);
    }

    public static Response get(String uri, HashMap<String, Object> params, String token) {
        // AuthenticationKey
        String authenticationKey = null;
        if(params.containsKey("AuthenticationKey"))
        {
            authenticationKey = params.get("AuthenticationKey").toString();
            params.remove("AuthenticationKey");
        }

        RequestSpecification query = given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().
                appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        if (token != null && token.equals(" ")) {
            query.accept("*/*")
                    .header("Content-Type", "application/json");

        } else if (params.containsKey("avoidContentType")) {
            query.accept("*/*")
                    .header("Authorization", "Bearer " + token);
        } else {
            query.accept("*/*")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token);

        }

        // EP Context
        if(epContext != null){
            query.header("EP-Context-Tag", epContext);
        }

        if(params.containsKey("hasBody")){
            String minifiedJson = new Minify().minify(params.get("jsonParams").toString());
            query.body(minifiedJson);
        }else {
            query.queryParams(params);
        }


        if(authenticationKey != null)
        {
            query.header("AuthenticationKey", authenticationKey);
        }

        LoggerHelper.logInfo("[ApiHelper/post]: Request log");
        query.log().all();

        Response response = query.when().get(uri);

        LoggerHelper.logInfo("[ApiHelper/post]: Response log");
        response.then().log().body();

        if(epContext != null){
            epContext = null;
        }

        return response;
    }

    public static Response put(String uri, HashMap<String, Object> params) throws GeneralSecurityException {
        return postOrPut(uri, params, " ", "PUT");
    }

    public static Response put(String uri, HashMap<String, Object> params, String token) throws GeneralSecurityException {
        return postOrPut(uri, params, token, "PUT");
    }

    public static Response post(String uri, HashMap<String, Object> params) throws GeneralSecurityException {
        return postOrPut(uri, params, " ", "POST");
    }

    public static Response post(String uri, HashMap<String, Object> params, String token) throws GeneralSecurityException {
        return postOrPut(uri, params, token, "POST");
    }

    public static Response postOrPut(String uri, HashMap<String, Object> params, String token, String method) throws GeneralSecurityException {
        RequestSpecification query;

        Cookies cookies = null;
        String authenticationKey = null;
        boolean useNsOauth = false;

        // COOKIES
        if(params.containsKey("cookies"))
        {
            cookies = (Cookies) params.get("cookies");
            params.remove("cookies");
        }

        // AuthenticationKey
        if(params.containsKey("AuthenticationKey"))
        {
            authenticationKey = params.get("AuthenticationKey").toString();
            params.remove("AuthenticationKey");
        }

        // Ouath 1
        if(params.containsKey("useNsOauth"))
        {
            useNsOauth = (boolean) params.get("useNsOauth");
            params.remove("useNsOauth");
        }

        // FORM-URLENCODED
        if(params.containsKey("contentType") && params.get("contentType") == "application/x-www-form-urlencoded"){
            query = given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig()
                    .defaultContentCharset("UTF-8")
                    .appendDefaultContentCharsetToContentTypeIfUndefined(false)));

            query.accept("*/*")
                    .contentType(ContentType.URLENC);

            for(String key: params.keySet()){
                if(key != "contentType")
                {
                    query.formParam(key, params.get(key));
                }
            }

        }else{
            query = given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig()
                    .appendDefaultContentCharsetToContentTypeIfUndefined(false)));

            if (token.equals(" ")) {
                query.accept("*/*")
                        .header("Content-Type", "application/json");

                if(params.containsKey("jsonParams")){
                    String minifiedJson = new Minify().minify(params.get("jsonParams").toString());
                    query.body(minifiedJson);
                }
            }
            else
            {
                query.accept("*/*")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token);

                if(params.containsKey("jsonParams")){
                    String minifiedJson = new Minify().minify(params.get("jsonParams").toString());
                    query.body(minifiedJson);
                }else {
                    query.body(params);
                }

            }
        }

        if(cookies != null)
        {
            query.cookies(cookies);
        }

        if(authenticationKey != null)
        {
            query.header("AuthenticationKey", authenticationKey);
        }

//        if(uri.contains(Env.get("projects.epcc.restEndpointNext"))){
//            query.header("x-vercel-protection-bypass", Env.get("projects.epcc.secret"));
//        }

       /*
        // THIS DOESNT WORK - CANDIDATE TO DELETE
        if(useNsOauth)
        {
            OAuthHmacSha256Signer signer = new OAuthHmacSha256Signer(Env.get("projects.netsuite.consumerSecret"));
            signer.setTokenSecret(Env.get("projects.netsuite.tokenSecret"));

            OAuthParameters oauthParameters = new OAuthParameters();
            oauthParameters.consumerKey = Env.get("projects.netsuite.consumerKey");
            oauthParameters.token = Env.get("projects.netsuite.accessToken");
            oauthParameters.signer = signer;
            oauthParameters.version = "1.0";
            oauthParameters.realm = Env.get("projects.netsuite.realm");
            oauthParameters.computeTimestamp();
            oauthParameters.computeNonce();
            GenericUrl genericUrl = new GenericUrl(uri);
            oauthParameters.signatureMethod = "HMAC-SHA256";
            oauthParameters.computeSignature("POST", genericUrl);

            String authHeader = oauthParameters.getAuthorizationHeader();

            query.header("Authorization", authHeader);
            query.urlEncodingEnabled(true);
            query.header("Cookie", "NS_ROUTING_VERSION=LAGGING");
            query.urlEncodingEnabled(false);

        }

        */

        LoggerHelper.logInfo("[ApiHelper/post]: Request log");
        query.log().all();

        Response response = null;
        if (method == "POST"){
            response = query.when().post(uri);
        } else if (method == "PUT") {
            response = query.when().put(uri);
        }


        LoggerHelper.logInfo("[ApiHelper/post]: Response log");
        response.then().log().body();

        return response;
    }

    public static Response patch(String uri, HashMap<String, Object> params, String token) {
        RequestSpecification query = given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().
                appendDefaultContentCharsetToContentTypeIfUndefined(false)));

        query.accept("*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(params.get("jsonParams"));

        LoggerHelper.logInfo("[ApiHelper/patch]: Request log");
        query.log().all();

        Response response = query.when().patch(uri);

        LoggerHelper.logInfo("[ApiHelper/patch]: Response log");
        response.then().log().body();

        return response;
    }

    public static Response delete(String uri, String token){
        RequestSpecification query = given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().
                appendDefaultContentCharsetToContentTypeIfUndefined(false)));


        query
                .accept("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token);


        LoggerHelper.logInfo("[ApiHelper/delete]: Request log");
        query.log().all();

        Response response = query.when().delete(uri);

        LoggerHelper.logInfo("[ApiHelper/delete]: Response log");
        response.then().log().body();

        return  response;
    }


    /**
     * Send a graphQlQuery
     * @param uri complete uri to make the query
     * @param graphQLQuery the query itself
     * @param params just in case some data is needed, not being used at the moment [TODO]
     * @param token Token received from the login, seems that it's not needed, sending in anyway [TODO]
     * @return Response
     */
    /*
    public static Response graph(String uri, GraphQLQuery graphQLQuery, HashMap<String, Object> params, String token){
        return graph(uri, graphQLQuery, params, token, null);
    }
*/
/*

    public static Response graph(String uri, GraphQLQuery graphQLQuery, HashMap<String, Object> params){
        String token = "";
        if(Env.get("projects.aws.last-token").equals("") || Env.get("projects.aws.useAuthentication").equals("false")){
            token = Env.get("projects.aws.default-token");
        }else {
            token = Env.get("projects.aws.last-token");
        }

        return graph(uri, graphQLQuery, params, token, null);
    }

 */

    /**
     * Send a graphQlQuery
     * @param uri complete uri to make the query
     * @param graphQLQuery the query itself
     * @param params just in case some data is needed, not being used at the moment [TODO]
     * @param token Token received from the login, seems that it's not needed, sending in anyway [TODO]
     * @param xApiKey x-api-key needed in some cases
     * @return Response
     */

//    public static Response graph(String uri, GraphQLQuery graphQLQuery, HashMap<String, Object> params, String token, String xApiKey){
//        RequestSpecification query;
//
//        query = given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig()
//                .defaultContentCharset("UTF-8")
//                .appendDefaultContentCharsetToContentTypeIfUndefined(false)));
//
//        query.accept("*/*")
//                .header("Content-Type", "application/json")
//                .header("Authorization", token)
//                //.header("ecp-app-source", "preview")
//                .body(graphQLQuery);
//
//        if(xApiKey != null)
//        {
//            query.header("x-api-key", xApiKey);
//        }
//
//        LoggerHelper.logInfo("[ApiHelper/graph]: Request log");
//        query.log().all();
//
//        Response response = query.when().post(uri);
//
//        Headers allHeaders = response.getHeaders();
//
//        if(allHeaders.hasHeaderWithName("x-auth-token")){
//            Env.set("projects.aws.last-token", allHeaders.getValue("x-auth-token"));
//        }
//
//        LoggerHelper.logInfo("[ApiHelper/graph]: Response log body");
//        response.then().log().body();
//        LoggerHelper.logInfo("[ApiHelper/graph]: Response log header");
//        response.then().log().headers();
//
//        return response;
//    }

}

