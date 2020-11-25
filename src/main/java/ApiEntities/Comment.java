package ApiEntities;

import Utils.ApiUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
Class Comment is a Java representation of a comment entity.
It has mirrored 'postId', 'id', 'name', 'email', and 'body' properties.
 */
public class Comment {
    //Equivalent of comment's postId from json
    private int postId;
    //Equivalent of comment's id from json
    private int id;
    //Equivalent of comment's name from json
    private String name;
    //Equivalent of comment's email from json
    private String email;
    //Equivalent of comment's body from json
    private String body;
    //Route's name
    public static final String entityName = "comments";

    /*
    Private constructor sets postId, id, name, email, and body values.
    Instance of Comment could be created by static methods.
     */
    private Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    /*
    Gets properties from JSON and creates instance of Comment.
    JSON must contain valid Comment.
     */
    public static Comment getCommentFromJson(JsonPath json) {
        int postId = json.getInt("[0].postId");
        int id = json.getInt("[0].id");
        String name = json.getString("[0].name");
        String email = json.getString("[0].email");
        String body = json.getString("[0].body");
        return new Comment(postId, id, name, email, body);
    }

    /*
    Creates instances of all comments in JSON and adds them into an ArrayList.
    JSON must contain valid Comment.
     */
    public static ArrayList<Comment> getListOfCommentsFromJson(JsonPath json) {
        ArrayList<Comment> comments = new ArrayList<>();
        List<HashMap<String, Object>> postsList = json.getList("$");
        postsList.parallelStream().
                forEach(map -> comments.add(new Comment((Integer) map.get("postId"), (Integer) map.get("id"),
                        (String) map.get("name"), (String) map.get("email"), (String) map.get("body"))));
        return comments;
    }

    //Searches comment by id and returns response with it
    public static Response getCommentByID(int id) {
        return ApiUtil.getResponseBySearchPath(entityName, "id", String.valueOf(id));
    }

    //Searches all post's comments by postId and returns response with it
    public static Response getCommentsByPostID(int postId) {
        return ApiUtil.getResponseBySearchPath(entityName, "postId", String.valueOf(postId));
    }

    //Returns value of comment's postId
    public int getPostId() {
        return postId;
    }

    //Returns value of comment's id
    public int getId() {
        return id;
    }

    //Returns value of comment's name
    public String getName() {
        return name;
    }

    //Returns value of comment's email
    public String getEmail() {
        return email;
    }

    //Returns value of comment's body
    public String getBody() {
        return body;
    }
}
