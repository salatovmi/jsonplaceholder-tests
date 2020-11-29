package ApiTests;

import ApiEntities.Comment;
import ApiEntities.Post;
import ApiEntities.User;
import Utils.FieldValidator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static Utils.ApiUtil.*;
import static Utils.ResponseHandler.*;
import static org.junit.Assert.*;

public class CommentsValidatorTest {

    //Set Base URI and Content type
    @Before
    public void setup() {
        setBaseURI("https://jsonplaceholder.typicode.com/");
        setContentType(ContentType.JSON);
    }

    /*
    Test search for a User by username and check if User exists.
    Then search for User's posts and check if they exist.
    Then search for Comments below each post and check response status for each request.
    Validate each email in comments.
     */
    @Test
    public void ValidateEmails() {
        Response res;
        Map<Integer, Response> commentResponses = new ConcurrentHashMap<>();
        List<Comment> comments = new ArrayList<>();
        String userName = "Delphine";

        res = User.getUserByUsername(userName);
        assertTrue(String.format("User Response Status isn't 200 for UserName = %s.", userName), checkResponseHasStatus200(res));

        assertFalse(String.format("User with UserName = %s doesn't exist.", userName), isEmpty(res));
        System.out.printf("Request for %s user is successful. Response status code is 200. \n", userName);
        User delphine = User.getUserFromJson(getJsonPath(res));
        res = Post.getPostsByUserID(delphine.getId());
        assertTrue("Post Response Status isn't 200.", checkResponseHasStatus200(res));

        assertFalse(String.format("User %s doesn't have any Posts", userName), isEmpty(res));
        System.out.printf("User %s has posts. Response status code is 200. \n", userName);
        List<Post> posts = Post.getListOfPostsFromJson(getJsonPath(res));
        posts.parallelStream().map(Post::getId).forEach(postId -> commentResponses.put(postId, Comment.getCommentsByPostID(postId)));
        List<Integer> badPostIds = checkAllResponsesHaveStatus200(commentResponses);
        assertEquals("Comment Response Status isn't 200 for Post id = " + badPostIds, 0, badPostIds.size());

        commentResponses.values().
                parallelStream().
                filter(resp -> !isEmpty(resp)).
                map(resp -> Comment.getListOfCommentsFromJson(getJsonPath(resp))).
                forEach(comments::addAll);
        assertNotEquals(String.format("%s's posts don't have comments.", userName),0, comments.size());
        System.out.printf("%s's posts have comments. Response status code is 200. \n", userName);
        List<String> emails = new ArrayList<>();
        comments.parallelStream().map(Comment::getEmail).forEach(emails::add);
        List<String> invalidEmails = emails.parallelStream().
                filter(email -> !FieldValidator.isEmailValid(email)).
                collect(Collectors.toList());
        assertEquals("Next emails are invalid:\n" + invalidEmails, 0, invalidEmails.size());
        System.out.println("All emails in comments are valid.");
    }

    //Reset Base URI
    @After
    public void after() {
        resetBaseURI();
    }
}
