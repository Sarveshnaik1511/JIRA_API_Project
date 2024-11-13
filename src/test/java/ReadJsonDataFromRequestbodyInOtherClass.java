


import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;

public class ReadJsonDataFromRequestbodyInOtherClass extends ReadJsonFile  {

    @Test
    public void readJsonData() throws IOException, ParseException {


//        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/Practice.json");
//        JSONParser jp = new JSONParser();
//        String requestBody = jp.parse(fr).toString();
        readJson();

        JSONObject js = new JSONObject(requestBody);
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

