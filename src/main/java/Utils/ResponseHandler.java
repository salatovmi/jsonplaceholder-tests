package Utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResponseHandler {
    //Verifies if the response is empty or not.
    public static boolean isEmpty(Response res) {
        JsonPath json = ApiUtil.getJsonPath(res);
        return (json.getList("$").size() == 0);
    }

    //Checks if Response Status Code is 200
    public static boolean checkResponseStatusIs200(Response res) {
        return res.getStatusCode() == 200;
    }
}
