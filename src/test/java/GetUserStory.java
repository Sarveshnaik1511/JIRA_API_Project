import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;

public class GetUserStory {
    private String cookie;
    private String storyId;
    @Test(priority = 1)
    public void login() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/LoginJira.json");
        JSONParser  jp = new JSONParser();
        String jsonBody = jp.parse(fr).toString();

        Response response=RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).body(jsonBody)
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

        // As story Id changes after every run, and we have to use this storyId everywhere
        // we can change it everywhere manually, but consumes lot of time and have to change every time
        // so we have saves it in variable, and it will get changed everytime and will get saved in the variable
        // we wil use that  variable in place of story id
        JSONObject js= new JSONObject(response.asString());
         storyId= js.get("key").toString();


    }

    @Test (priority = 3)
    public void getUserStory(){

        // to get created user story, we dont have to read any file
        // as we dont have any file to read this
        // we have to get the user story using CRUD operation and by using principle of RestAssured structure / methods

       Response response= RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie",cookie)
                .when().get("/rest/api/2/issue/"+storyId).then().log().all().extract().response();

    }

}
