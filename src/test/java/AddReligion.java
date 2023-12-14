import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddReligion {

    @Test(priority = 1)
    public void createFileForHindu() {
        modifyAndSaveToFile("babyNamesMuslim.json", "Muslim", "babyNamesMuslimReligion.json");
    }

//    @Test(priority = 2)
//    public void createFileForMuslim() {
//        modifyAndSaveToFile("babyNames.json", "Hindu", "babyNamesHinduReligion.json");
//    }

    private void modifyAndSaveToFile(String sourceFilePath, String religion, String targetFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<JsonNode> nameList = objectMapper.readValue(new File(sourceFilePath), new com.fasterxml.jackson.core.type.TypeReference<List<JsonNode>>() {
            });
            Map<Character, List<JsonNode>> segregatedMap = new HashMap<>();



            for (JsonNode nameNode : nameList) {
                ((ObjectNode) nameNode).put("Religion", religion);
            }

            String modifiedJsonString = objectMapper.writeValueAsString(nameList);

            objectMapper.writeValue(new File(targetFilePath), nameList);

            System.out.println(modifiedJsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
