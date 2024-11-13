import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GetPropertiesAndQueryParameter {
    private String url;
    private String cookie;
    private String storyId;

    @Test (priority = 1)
    public void loginJira() throws IOException, ParseException {
        // to read login json file
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/LoginJira.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        // to read properties file
        FileReader prop = new FileReader("src/main/java/com/sarvesh/Files/data.properties");
        Properties pr = new Properties();
        // to load current properties
        pr.load(prop);

        // getProperty() method is used to get the data from properties file
        System.out.println(pr.getProperty("name"));

        // here I have get the url stored in properties file and stored as globle
        url =pr.getProperty("url");

        Response response= RestAssured.given().baseUri(url).body(requestBody).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();
        //assertion used
        System.out.println(response.asString());
        Assert.assertEquals(response.getStatusCode(),200);

        // used jsonobject class to get the cookie value and save in globle variable
        JSONObject js = new JSONObject(response.asString());
        cookie="JSESSIONID=" + js.getJSONObject("session").get("value").toString();

        System.out.println("this is cookie ="+cookie);

    }
    @Test (priority = 2)
    public void createUserStory() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/CreateUserStory.json");
        JSONParser jp = new JSONParser();
        String requestBody =jp.parse(fr).toString();

        Response response =RestAssured.given().baseUri(url).body(requestBody).contentType(ContentType.JSON).header("cookie",cookie)
                .when().post("/rest/api/2/issue").then().log().all().extract().response();

        System.out.println(response.asString());

        JSONObject js = new JSONObject(response.asString());

        // here we get the storyId and store in globle valiable so it will automatically get updated after every run
        // o we don't have to update it manually to get the particular data from particular id
        storyId=js.get("key").toString();

    }

    @Test (priority = 3)
    public void getUserStory(){
           Response response= RestAssured.given().header("cookie",cookie).baseUri(url).contentType(ContentType.JSON)
                   // have filtered particular data using queryParam method which accepts key and value
                   .queryParam("fields","status").queryParam("fields","priority")
                .when().get("/rest/api/2/issue/"+storyId)
                .then().log().all().extract().response();
        System.out.println(response.asString());
    }
}
