import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AddAttachments {
    private String cookie;
    private String storyId;

    @Test(priority = 1)
    public void login() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/LoginJira.json");
        JSONParser jp = new JSONParser();
        String jsonBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).body(jsonBody)
                .when().post("/rest/auth/1/session").then().log().all().extract().response();

        String r = response.asString();
        JSONObject js = new JSONObject(r);
        cookie = "JSESSIONID=" + js.getJSONObject("session").get("value").toString();

        // in the assertEquals assertion, first is actual result and then expected result
        Assert.assertEquals(response.getStatusCode(), 200);



    }

    @Test(priority = 2)
    public void createUserStory() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/CreateUserStory.json");
        JSONParser jp = new JSONParser();
        String jsonBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:8080").body(jsonBody).contentType(ContentType.JSON).header("Cookie", cookie)
                .when().post("/rest/api/2/issue").then().log().all().extract().response();
        System.out.println(response.asString());
        Assert.assertEquals(response.getStatusCode(), 201);
        JSONObject js = new JSONObject(response.asString());
        storyId = js.get("key").toString();


    }

    @Test(priority = 3)
    public void addAttachment() {
        File attachment = new File("src/main/java/com/sarvesh/Files/attachment.png");

        Response response = RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.MULTIPART).header("Cookie", cookie).header("X-Atlassian-Token", "no-check")
                .multiPart("file", attachment)
                .when().post("/rest/api/2/issue/" + storyId + "/attachments")
                .then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);

    }

}
