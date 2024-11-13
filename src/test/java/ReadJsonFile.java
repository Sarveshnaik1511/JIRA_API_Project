import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadJsonFile {

    String requestBody;

    @Test

    public void readJson() throws IOException, ParseException {

        // FileReader is a java Class which accepts the path of the file and helps to read the data from the file
        // basically it reads the file
        FileReader fr = new FileReader("src/main/java/com/sarvesh/Files/Practice.json");

        // JsonParser class methods helps to convert the JSON data to String form
        JSONParser jp = new JSONParser();

        // parse method helps to convert the file data into desired form
        requestBody = jp.parse(fr).toString();
        System.out.println(requestBody);


    }

}
