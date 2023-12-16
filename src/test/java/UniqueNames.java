import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UniqueNames {

    @Test
    public void compareAndStoreUniqueNames() {
        try {
            ArrayNode jsonArray1 = readJsonFile("babyNamesUnstring.json");
            ArrayNode jsonArray2 = readJsonFile("babyNamesHinduReligion.json");
            ArrayNode jsonArray3 = readJsonFile("babyNamesMuslimReligion.json");

            Set<NameAttributes> uniqueNames = new HashSet<>();
            addNames(uniqueNames, jsonArray1);
            addNames(uniqueNames, jsonArray2);
            addNames(uniqueNames, jsonArray3);

            System.out.println("Unique Names: " + uniqueNames);

            ArrayNode uniqueNamesArray = new ObjectMapper().createArrayNode();
            for (NameAttributes nameAttributes : uniqueNames) {
                ObjectNode nameObject = new ObjectMapper().createObjectNode();
                nameObject.put("name", nameAttributes.getName());
                nameObject.put("gender", nameAttributes.getGender());
                nameObject.put("meaning", nameAttributes.getMeaning());
                nameObject.put("rashi", nameAttributes.getRashi());
                nameObject.put("nakshatra", nameAttributes.getNakshatra());
                nameObject.put("religion", nameAttributes.getReligion());

                uniqueNamesArray.add(nameObject);
            }

            writeJsonFile("uniqueNames.json", uniqueNamesArray);

            Assert.assertTrue(uniqueNames.size() > 0, "At least one unique name should be present.");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("IOException occurred while reading or writing JSON files.");
        }
    }

    private void addNames(Set<NameAttributes> uniqueNames, ArrayNode jsonArray) {
        for (JsonNode jsonNode : jsonArray) {
            JsonNode nameNode = jsonNode.get("name");
            if (nameNode != null && !nameNode.isNull()) {
                String name = nameNode.asText();
                String gender = jsonNode.has("gender") ? jsonNode.get("gender").asText("") : "";
                String meaning = jsonNode.has("meaning") ? jsonNode.get("meaning").asText("") : "";
                String rashi = jsonNode.has("rashi") ? jsonNode.get("rashi").asText("") : "";
                String nakshatra = jsonNode.has("nakshatra") ? jsonNode.get("nakshatra").asText("") : "";
                String religion = jsonNode.has("religion") ? jsonNode.get("religion").asText("") : "";

                NameAttributes nameAttributes = new NameAttributes(name, gender, meaning, rashi, nakshatra, religion);
                uniqueNames.add(nameAttributes);
            }
        }
    }


    private ArrayNode readJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), ArrayNode.class);
    }

    private void writeJsonFile(String filePath, ArrayNode jsonContent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), jsonContent);
    }

    private static class NameAttributes {
        private final String name;
        private final String gender;
        private final String meaning;
        private final String rashi;
        private final String nakshatra;
        private final String religion;

        public NameAttributes(String name, String gender, String meaning, String rashi, String nakshatra, String religion) {
            this.name = name;
            this.gender = gender;
            this.meaning = meaning;
            this.rashi = rashi;
            this.nakshatra = nakshatra;
            this.religion = religion;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getMeaning() {
            return meaning;
        }

        public String getRashi() {
            return rashi;
        }

        public String getNakshatra() {
            return nakshatra;
        }

        public String getReligion() {
            return religion;
        }
    }
}
