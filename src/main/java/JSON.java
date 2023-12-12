import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JSON {
    public void Write(String file_name, String expression)
    {
        // Запись математического выражения в файл JSON
        JSONObject obj = new JSONObject();
        obj.put("MathExpression", expression);

        try (FileWriter file = new FileWriter(file_name)) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Read(String file_name)
    {
        // Чтение математического выражения из файла JSON
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file_name)) {
            Object jsonObj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) jsonObj;
            String mathExpression = (String) jsonObject.get("MathExpression");

            System.out.println("Math Expression from JSON file: " + mathExpression);
            return mathExpression;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
