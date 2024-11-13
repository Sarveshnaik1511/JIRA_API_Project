import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

public class GetUpdatedUserStory {

    private String cookie;
    private String storyId;
    @Test(priority = 1)
    public void login() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/LoginJira.json");
        JSONParser jp = new JSONParser();
        String jsonBody = jp.parse(fr).toString();

        Response response= RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).body(jsonBody)
                .when().post("/rest/auth/1/session").then().log().all().extract().response();

        String r= response.asString();
        JSONObject js = new JSONObject(r);
        cookie= "JSESSIONID="+ js.getJSONObject("session").get("value").toString();

    }
    @Test (priority = 2)
    public void createUserStory() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/CreateUserStory.json");
        JSONParser jp = new JSONParser();
        String jsonBody= jp.parse(fr).toString();

        Response response= RestAssured.given().baseUri("http://localhost:8080").body(jsonBody).contentType(ContentType.JSON).header("Cookie",cookie)
                .when().post("/rest/api/2/issue").then().log().all().extract().response();
        System.out.println(response.asString());

        JSONObject js= new JSONObject(response.asString());
        storyId= js.get("key").toString();


    }

    @Test (priority = 3)
    public void getUserStory(){
        Response response= RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie",cookie)
                .when().get("/rest/api/2/issue/"+storyId).then().log().all().extract().response();

    }

    @Test (priority = 4)
    public void updateUserStory() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/UpdateUserStory.json");
        JSONParser js = new JSONParser();
        String jsonBody= js.parse(fr).toString();

        Response response= RestAssured.given().baseUri("http://localhost:8080").body(jsonBody).contentType(ContentType.JSON).header("Cookie",cookie)
                .when().put("/rest/api/2/issue/"+storyId)
                .then().log().all().extract().response();


    }

    @Test (priority = 5)
    public void getUpdatedUserStory(){

        // we have to get the updated userstory, so we will exicute the get method here
        // in the bae uri, we will give the endpoint with storyId
        Response response= RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie", cookie)
                .when().get("/rest/api/2/issue/"+storyId)
                .then().log().all().extract().response();

        System.out.println(response.asString());
    }

}
