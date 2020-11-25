package ApiEntities;

import Utils.ApiUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
Class Post is a Java representation of a post entity.
It has mirrored 'userId', 'id', 'title', and 'body' properties.
 */
public class Post {
    //Equivalent of post's userId from json
    private int userId;
    //Equivalent of post's id from json
    private int id;
    //Equivalent of post's title from json
    private String title;
    //Equivalent of post's body from json
    private String body;
    //Route's name
    public static final String entityName = "posts";

    /*
    Private constructor sets userId, id, title, and body values.
    Instance of Post could be created by static methods.
     */
    private Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    /*
    Gets properties from JSON and creates instance of Post.
    JSON must contain valid Post.
     */
    public static Post getPostFromJson(JsonPath json) {
        int userId = json.getInt("[0].userId");
        int id = json.getInt("[0].id");
        String title = json.getString("[0].title");
        String body = json.getString("[0].body");
        return new Post(userId, id, title, body);
    }

    /*
    Creates instances of all posts in JSON and adds them into an ArrayList.
    JSON must contain valid Post.
     */
    public static ArrayList<Post> getListOfPostsFromJson(JsonPath json) {
        ArrayList<Post> posts = new ArrayList<>();
        List<HashMap<String, Object>> postsList = json.getList("$");
        postsList.parallelStream().
                forEach(map -> posts.add(new Post((Integer) map.get("userId"), (Integer) map.get("id"),
                        (String) map.get("title"), (String) map.get("body"))));
        return posts;
    }

    //Searches post by id and returns response with it
    public static Response getPostByID(int id) {
        return ApiUtil.getResponseBySearchPath(entityName, "id", String.valueOf(id));
    }

    //Searches all user's posts by userId and returns response with it
    public static Response getPostsByUserID(int userId) {
        return ApiUtil.getResponseBySearchPath(entityName, "userId", String.valueOf(userId));
    }

    //Returns value of post's userId
    public int getUserId() {
        return userId;
    }

    //Returns value of post's id
    public int getId() {
        return id;
    }

    //Returns value of post's title
    public String getTitle() {
        return title;
    }

    //Returns value of post's body
    public String getBody() {
        return body;
    }
}
