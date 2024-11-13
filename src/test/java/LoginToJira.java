import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.testng.annotations.Test;


import java.io.FileReader;
import java.io.IOException;

public class LoginToJira {
     private String cookie;

    @Test (priority = 1)
    public void loginToJira() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/LoginJira.json");
        JSONParser jp = new JSONParser();
        String requestBody= jp.parse(fr).toString();

        // we have one architecture of methods called given, when and then
        // RestAssured is a class which has all these methods,
        // we have to call the methods in order starting from given, when and then
        // given accepts pre-conditions like body, baseuri, content-type, path-parameter etc
        // when accepts http method with endpoint
        // and in then, we do validation
    Response response=  RestAssured.given().baseUri("http://localhost:8080/").contentType(ContentType.JSON).body(requestBody)
            .when().post("/rest/auth/1/session")
            // log means the console,
            // all means all the things appears in console,
            // so basically, log will print everything on console
            // if we want particular data, for example body, than instead of all we will give body
            // we use all method, as we can get particular data from different methods of Response interface
            // we will extract it using extract method
            // at last we want to store as a response, so we use response method
            // this returns Response interface
            .then().log().all().extract().response();

        // to print Status code
        System.out.println("Status code: "+response.getStatusCode());

        // print the response
        System.out.println("Response print for understanding"+response.asString());


        JSONObject js = new JSONObject(response.asString());
        // here we have stored value of Json response, in this case value wi.e we will use it as cookie fir authentication.
        // we have declared globally as we want to use the cookie in other tests.
        // we have concatenated the JSESSIONID, as this will be common and cookie value will get changed for every login
         cookie ="JSESSIONID="+js.getJSONObject("session").get("value").toString();

        // here we have printed cookie for understanding
        System.out.println(cookie);
    }

    @Test (priority = 2)
    public void createJira(){
        System.out.println(cookie);
    }
}
