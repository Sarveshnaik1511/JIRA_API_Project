import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TaskCrudOperations {

    private String cookie;
    private String issueId;

    @Test (priority = 1)
    public void loginJira() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/LoginJira.json");
        JSONParser jp = new JSONParser();
        String jsonBody= jp.parse(fr).toString();

       Response response=  RestAssured.given().baseUri("http://localhost:8080").body(jsonBody).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();
        JSONObject js = new JSONObject(response.asString());
        cookie ="JSESSIONID="+js.getJSONObject("session").get("value").toString();
        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test (priority = 2)
    public void createTask() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/CreateTask.json");
        JSONParser jp = new JSONParser();
        String jsonBody= jp.parse(fr).toString();

        Response response=  RestAssured.given().baseUri("http://localhost:8080").body(jsonBody).contentType(ContentType.JSON).header("Cookie", cookie)
                .when().post("/rest/api/2/issue")
                .then().log().all().extract().response();
        Assert.assertEquals(response.getStatusCode(),201);

        JSONObject js = new JSONObject(response.asString());
        issueId =js.get("key").toString();
    }

    @Test (priority = 3)
    public void getTask(){
        Response response= RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie",cookie)
                .when().get("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();
        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test (priority = 4)
    public void updateTask() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/UpdateTask.json");
        JSONParser jp = new JSONParser();
        String jsonBody= jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie",cookie).body(jsonBody)
                .when().put("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();
        Assert.assertEquals(response.getStatusCode(),204);

    }

    @Test (priority = 5)
    public void getUpdatedTask(){
      Response response=  RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie", cookie)
                .when().get("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();
      Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test (priority = 6)
    public void deleteTask(){
        Response response=RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie",cookie)
                .when().delete("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(),204);
    }

    @Test (priority = 7)
    public void getDeletedTask(){
       Response response = RestAssured.given().baseUri("http://localhost:8080").contentType(ContentType.JSON).header("Cookie",cookie)
                .when().get("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();
       Assert.assertEquals(response.getStatusCode(),404);
    }


}
