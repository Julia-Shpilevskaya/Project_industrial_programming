package file_types;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JSONTest {

    @Test
    void Write() {
        String text = "33+99*6/(7-4)";

        JSON json = new JSON();

        json.Write("file1.json",text);

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("file1.json")) {
            Object jsonObj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) jsonObj;
            String mathExpression = (String) jsonObject.get("MathExpression");

            Assert.assertEquals(text,mathExpression);
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void Read() {
        String expression = "33+99/(7-4)";

        JSONObject obj = new JSONObject();
        obj.put("MathExpression", expression);

        try (FileWriter file = new FileWriter("file10.json")) {
            file.write(obj.toJSONString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JSON json = new JSON();

        String text_in_file = json.Read("file10.json");

        Assert.assertEquals(expression,text_in_file);


    }
}