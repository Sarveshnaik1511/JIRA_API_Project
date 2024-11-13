import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadPerticularJsonDataFromSameClass {

    @Test
    public void readJson() throws IOException, ParseException {
        // this will read the data of the file we have passed in the arguments
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/Practice.json");
        JSONParser jp = new JSONParser();
        // parse method will convert the JSON data to string
        String requestBody = jp.parse(fr).toString();

        // this will get the string data from the requestbody variable that we have given to parse method after converting the JSON data to string
        JSONObject js = new JSONObject(requestBody);

        //getJSONArray(), [] is the method to enter into the array of json data which will be in []
        // we have to give key as an argument for getJSONArray() method
        //getJSONObject, {} is the method to get to the object which wil be in {}
        //we have to pass the index of the data we want to get to.

        String playerFirstNameFromGroupAIndex1 = js.getJSONArray("groupA").getJSONObject(1).get("firstName").toString();
        System.out.println(playerFirstNameFromGroupAIndex1);

        String playerLastNameFromGroupAIndex1 = js.getJSONArray("groupA").getJSONObject(1).get("lastName").toString();
        System.out.println(playerLastNameFromGroupAIndex1);

        String playerSalaryFromGroupAIndex1 = js.getJSONArray("groupA").getJSONObject(1).get("salary").toString();
        System.out.println(playerSalaryFromGroupAIndex1);

        String viratSalary = js.getJSONArray("groupA").getJSONObject(0).get("salary").toString();
        System.out.println("virat's salary is: " + viratSalary);

        String rohitAge = js.getJSONArray("groupA").getJSONObject(1).get("age").toString();
        System.out.println("Rohit Sharma's age is: " + rohitAge);

        String klLastName = js.getJSONArray("groupB").getJSONObject(0).get("lastName").toString();
        System.out.println("KL's last name is: " + klLastName);

        String msTeam = js.getJSONArray("groupB").getJSONObject(1).get("team").toString();
        System.out.println("Ms team name is: " + msTeam);


    }
}
