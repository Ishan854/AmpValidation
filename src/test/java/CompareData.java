
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileReader;

public class CompareData {

    @Test
    public void compareData() {
        try {
            String fileName = "babyNamesUnstring.json";
            FileReader reader = new FileReader(fileName);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);

            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                String name = (String) jsonObject.get("name");
                String gender = (String) jsonObject.get("gender");
                String meaning = (String) jsonObject.get("meaning");

                System.out.println("Name: " + name);
                System.out.println("Gender: " + gender);
                System.out.println("Meaning: " + meaning);
            } else if (obj instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) obj;
                for (Object jsonArrayElement : jsonArray) {
                    if (jsonArrayElement instanceof JSONObject) {
                        JSONObject nameObject = (JSONObject) jsonArrayElement;
                        String name = (String) nameObject.get("name");
                        String gender = (String) nameObject.get("gender");
                        String meaning = (String) nameObject.get("meaning");

                        System.out.println("Name: " + name);
                        System.out.println("Gender: " + gender);
                        System.out.println("Meaning: " + meaning);
                        System.out.println("------");
                    }
                }
            } else {
                System.out.println("The parsed object is neither a JSONObject nor a JSONArray.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
