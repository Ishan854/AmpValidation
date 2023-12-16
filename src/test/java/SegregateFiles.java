//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.testng.annotations.Test;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class SegregateFiles {
//
////    @Test(priority = 1)
////    public void createFileForMuslim() {
////        segregateFiles("babyNamesMuslimReligion.json");
////    }
//
//    @Test(priority = 1)
//    public void createFileForMuslim() {
//        segregateFiles("uniqueNames.json");
//    }
//
//    private void segregateFiles(String sourceFilePath) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            List<JsonNode> nameList = objectMapper.readValue(new File(sourceFilePath), new com.fasterxml.jackson.core.type.TypeReference<List<JsonNode>>() {
//            });
//
//            Map<Character, List<JsonNode>> segregatedMap = new HashMap<>();
//
//            for (JsonNode nameNode : nameList) {
//                String name = nameNode.get("Name").asText();
//                char firstLetter = name.charAt(0);
//
//                List<JsonNode> nameNodes = segregatedMap.computeIfAbsent(firstLetter, k -> new ArrayList<>());
//
//                nameNodes.add(nameNode);
//            }
//
//            for (Map.Entry<Character, List<JsonNode>> entry : segregatedMap.entrySet()) {
//                char firstLetter = entry.getKey();
//                String targetFilePath = firstLetter + ".json";
//                objectMapper.writeValue(new File(targetFilePath), entry.getValue());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}

//---------------------------

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.testng.annotations.Test;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class SegregateFiles {
//
//    @Test(priority = 1)
//    public void createFileForMuslim() {
//        segregateFiles("uniqueNames.json");
//    }
//
//    private void segregateFiles(String sourceFilePath) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            List<JsonNode> nameList = objectMapper.readValue(new File(sourceFilePath),
//                    new com.fasterxml.jackson.core.type.TypeReference<List<JsonNode>>() {
//                    });
//
//            Map<Character, List<JsonNode>> segregatedMap = new HashMap<>();
//
//            for (JsonNode nameNode : nameList) {
//                JsonNode nameField = nameNode.get("name");
//
//                if (nameField != null && nameField.isTextual()) {
//                    String name = nameField.asText();
//                    char firstLetter = name.charAt(0);
//
//                    List<JsonNode> nameNodes = segregatedMap.computeIfAbsent(firstLetter, k -> new ArrayList<>());
//                    nameNodes.add(nameNode);
//                } else {
//                    System.err.println("Skipping entry with null or non-text 'name' field: " + nameNode);
//                }
//            }
//
//            for (Map.Entry<Character, List<JsonNode>> entry : segregatedMap.entrySet()) {
//                char firstLetter = entry.getKey();
//                String targetFilePath = firstLetter + ".json";
//                objectMapper.writeValue(new File(targetFilePath), entry.getValue());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//-----------------------------------------------------------------------------

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegregateFiles {

    @Test(priority = 1)
    public void createFileForMuslim() {
        segregateFiles("uniqueNames.json");
    }

    private void segregateFiles(String sourceFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<JsonNode> nameList = objectMapper.readValue(new File(sourceFilePath),
                    new com.fasterxml.jackson.core.type.TypeReference<List<JsonNode>>() {
                    });

            Map<String, List<JsonNode>> segregatedMap = new HashMap<>();

            for (JsonNode nameNode : nameList) {
                JsonNode nameField = nameNode.get("name");

                if (nameField != null && nameField.isTextual()) {
                    String name = nameField.asText();
                    String firstLetter = name.substring(0, 1).toLowerCase();  // Convert to lowercase for uniformity

                    List<JsonNode> nameNodes = segregatedMap.computeIfAbsent(firstLetter, k -> new ArrayList<>());
                    nameNodes.add(nameNode);
                } else {
                    System.err.println("Skipping entry with null or non-text 'name' field: " + nameNode);
                }
            }

            for (Map.Entry<String, List<JsonNode>> entry : segregatedMap.entrySet()) {
                String firstLetter = entry.getKey();
                String targetFilePath = firstLetter + ".json";
                objectMapper.writeValue(new File(targetFilePath), entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

