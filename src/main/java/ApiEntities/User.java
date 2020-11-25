package ApiEntities;

import Utils.ApiUtil;
import Utils.ResponseHandler;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Optional;

/*
Class User is a Java representation of user entity.
It has mirrored 'id' and 'username' properties.
Could be extended with other properties.
 */
public class User {
    //Equivalent of user's id from json
    private int id;
    //Equivalent of user's username from json
    private String userName;
    //Route's name
    public static final String entityName = "users";

    /*
    Private constructor sets id and userName values.
    Instance of User could be created by static methods.
     */
    private User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    /*
    Gets properties from JSON and creates instance of User.
    JSON must contain valid User.
     */
    public static User getUserFromJson(JsonPath json) {
        int id = json.getInt("[0].id");
        String user = json.getString("[0].username");
        return new User(id, user);
    }

    //Searches user by id and returns response with it
    public static Response getUserByID(int id) {
        return ApiUtil.getResponseBySearchPath(entityName, "id", String.valueOf(id));
    }

    //Searches user by userName and returns response with it
    public static Response getUserByUsername(String username) {
        return ApiUtil.getResponseBySearchPath(entityName, "username", username);
    }

    //Returns value of user's id
    public int getId() {
        return id;
    }

    //Returns value of user's userName
    public String getUserName() {
        return userName;
    }
}
