package Utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    //Verifies if the response is empty or not.
    public static boolean isEmpty(Response res) {
        JsonPath json = ApiUtil.getJsonPath(res);
        return (json.getList("$").size() == 0);
    }

    //Checks if Response Status Code is 200
    public static boolean checkResponseHasStatus200(Response res) {
        return res.getStatusCode() == 200;
    }

    /*
    Method iterates through Map of keys and responses and checks that each response has Status Code 200.
    Returns List of keys linked with responses have wrong Status Code.
     */
    public static <K> List<K> checkAllResponsesHaveStatus200(Map<K, Response> responsesMap) {
        ArrayList<K> invalidKeys = new ArrayList<>();
        responsesMap.forEach((key, resp) ->
        {
            if (!checkResponseHasStatus200(resp)) {
                invalidKeys.add(key);
            }
        });
        return invalidKeys;
    }
}
