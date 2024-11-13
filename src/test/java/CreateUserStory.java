import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;



import java.io.FileReader;
import java.io.IOException;

public class CreateUserStory {

    private String cookie;
    @Test (priority = 1)
    public void loginJira() throws IOException, ParseException {
        // Read the json login file
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/LoginJira.json");
        JSONParser jp = new JSONParser();
        String jsonBody= jp.parse(fr).toString();

        // extract the response and store in variable
       Response response= RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).body(jsonBody)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();

       // read the cookie value and stored in variable and make it global, concatenate it with its KEY
        JSONObject js = new JSONObject(response.asString());
        cookie ="JSESSIONID="+ js.getJSONObject("session").get("value").toString();

    }

    @Test (priority = 2)
    public void createUserStory() throws IOException, ParseException {
        // read userStory file
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/CreateUserStory.json");
        JSONParser jp = new JSONParser();
        String responseBody = jp.parse(fr).toString();

        // Create user story
        // if you want print only response or only status code or any other particular data, we have to store the response in a variable
        // this returns Response interface
       Response response = RestAssured.given().baseUri("http://localhost:8080").body(responseBody).header("Cookie", cookie).contentType(ContentType.JSON)
                           .when().post("/rest/api/2/issue").then().log().all().extract().response();
        System.out.println(response.asString());


    }
}
