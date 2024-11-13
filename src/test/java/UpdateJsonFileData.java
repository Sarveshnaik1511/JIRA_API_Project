
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UpdateJsonFileData {
    @Test
    public void updateJsonFileData() throws IOException, ParseException {

        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/Practice.json");
        JSONParser jp = new JSONParser();

        String requestBody = jp.parse(fr).toString();

        JSONObject js = new JSONObject(requestBody);

        // To update the data from the JSON File, we use put method to change the particular data from the json file
        // put method accepts the arguments as KEY and Value pair. i.e what to change and what value to be assigned
        js.getJSONArray("groupA").getJSONObject(1).put("salary", "20 cr");
        js.getJSONArray("groupA").getJSONObject(1).put("team","KKR");

        // the updates as been done only in the JSON Data that we have converted to string and stored in a variable, and variable assigned to JSONObject class
        // the main file doesn't get affected
        // here in our case it has been assigned to reference of JSONObject class, so the data in variable js has been changed.
        // so if we print js, we will get updated data.
        // but if we print requestBody, the data remained unchanged


        System.out.println(js);
        System.out.println(requestBody);


    }
}




