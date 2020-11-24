package Utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ApiUtil {
    //Global path would be added to URI
    public static String path;

    //We should set BaseURI before the test
    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    //We should reset BaseURI after the test
    public static void resetBaseURI() {
        RestAssured.baseURI = null;
    }

    //We should set content type before the test. It could be JSON or XML
    public static void setContentType (ContentType type){
        given().contentType(type);
    }

    //Creates and sets path for search like '/comments?postId=1'
    public static void setSearchPath(String entity, String key, String value) {
        path = entity + "?" + key + "=" + value;
    }

    //Sends 'path' as a parameter to a 'get' method and returns a Response
    public static Response getResponse() {
        return get(path);
    }

    //Converts Response to JsonPath
    public static JsonPath getJsonPath(Response response) {
        String jsonPath = response.asString();
        return new JsonPath(jsonPath);
    }

    //Sets search path, gets response and returns it
    public static Response getResponseBySearchPath(String entity, String key, String value) {
        setSearchPath(entity, key, value);
        return getResponse();
    }
}
