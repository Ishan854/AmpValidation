import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UniqueNames {

    @Test
    public void testRemoveDuplicates() {
        Set<String> uniqueNames = new HashSet<>();

        try {
            // Read and process each JSON file
            processJsonFile("babyNamesUnstring.json", uniqueNames);
            processJsonFile("babyNamesHinduReligion.json", uniqueNames);
            processJsonFile("babyNamesMuslimReligion.json", uniqueNames);

            // Create a new JSON file with unique names
            writeUniqueNamesToJsonFile(uniqueNames, "uniqueNames.json");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("IOException occurred: " + e.getMessage());
        }
    }

    private void processJsonFile(String filePath, Set<String> uniqueNames) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(filePath));

        Iterator<JsonNode> iterator = jsonNode.iterator();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
            String name = node.get("name").asText();
            uniqueNames.add(name);
        }
    }

    private void writeUniqueNamesToJsonFile(Set<String> uniqueNames, String outputFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File outputFile = new File(outputFilePath);

        ArrayNode uniqueNameNodes = objectMapper.createArrayNode();

        for (String name : uniqueNames) {
            // Create a JSON object for each unique name
            ObjectNode uniqueNameNode = objectMapper.createObjectNode();

            // Iterate through each attribute and add it to the JSON object
            for (String attribute : Arrays.asList("meaning", "gender", "rashi", "nakshatra", "religion")) {
                // Assuming each attribute is a simple string
                String attributeValue = "Replace with actual " + attribute + " for " + name;
                uniqueNameNode.put(attribute, attributeValue);
            }

            // Add the JSON object to the array
            uniqueNameNodes.add(uniqueNameNode);
        }

        // Write the entire array to the output file
        objectMapper.writeValue(outputFile, uniqueNameNodes);
    }
}
