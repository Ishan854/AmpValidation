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

public class UniqueNamesTest {

    @Test
    public void compareAndStoreUniqueNames() {
        try {
            ArrayNode jsonArray2 = readJsonFile("babyNamesHinduReligion.json");
            ArrayNode jsonArray3 = readJsonFile("babyNamesMuslimReligion.json");
            ArrayNode jsonArray4 = readJsonFile("babyNamesChristianReligion.json");
            ArrayNode jsonArray5 = readJsonFile("babyNamesSikhReligion.json");
            ArrayNode jsonArray6 = readJsonFile("babyNamesBuddhismReligion.json");

            Set<NameAttributes> uniqueNames = new HashSet<>();

            addNames(uniqueNames, jsonArray2);
            addNames(uniqueNames, jsonArray3);
            addNames(uniqueNames, jsonArray4);
            addNames(uniqueNames, jsonArray5);
            addNames(uniqueNames, jsonArray6);

            ArrayNode uniqueNamesArray = convertSetToArrayNode(uniqueNames);

            writeJsonFile("uniqueNamesTest.json", uniqueNamesArray);

            int totalUniqueNames = uniqueNames.size();
            System.out.println("Total Unique Names: " + totalUniqueNames);

            Assert.assertTrue(totalUniqueNames > 0, "At least one unique name should be present.");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("IOException occurred while reading or writing JSON files.");
        }
    }

    private void addNames(Set<NameAttributes> uniqueNames, ArrayNode jsonArray) {
        ArrayNode jsonArrayUnstring = null;
        try {
            jsonArrayUnstring = readJsonFile("babyNamesUnstring.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (JsonNode jsonNode : jsonArray) {
            JsonNode nameNode = jsonNode.get("name");
            if (nameNode != null && !nameNode.isNull()) {
                String name = nameNode.asText();
                String gender = jsonNode.has("gender") ? jsonNode.get("gender").asText("") : "";
                String meaning = jsonNode.has("meaning") ? jsonNode.get("meaning").asText("") : "";
                String religion = jsonNode.has("religion") ? jsonNode.get("religion").asText("") : "";

                boolean isNameInUnstring = isNameInUnstring(name, jsonArrayUnstring);

                if (!isNameInUnstring) {
                    NameAttributes nameAttributes = new NameAttributes(name, gender, meaning, religion);
                    uniqueNames.add(nameAttributes);
                }
            }
        }
    }

    private boolean isNameInUnstring(String name, ArrayNode jsonArrayUnstring) {
        for (JsonNode jsonNode : jsonArrayUnstring) {
            JsonNode unstringNameNode = jsonNode.get("name");
            if (unstringNameNode != null && !unstringNameNode.isNull() && name.equals(unstringNameNode.asText())) {
                return true;
            }
        }
        return false;
    }

    private ArrayNode convertSetToArrayNode(Set<NameAttributes> uniqueNames) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode uniqueNamesArray = objectMapper.createArrayNode();

        for (NameAttributes nameAttributes : uniqueNames) {
            ObjectNode nameObject = objectMapper.createObjectNode();
            nameObject.put("name", nameAttributes.getName());
            nameObject.put("gender", nameAttributes.getGender());
            nameObject.put("meaning", nameAttributes.getMeaning());
            nameObject.put("religion", nameAttributes.getReligion());
            uniqueNamesArray.add(nameObject);
        }

        return uniqueNamesArray;
    }

    private ArrayNode readJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), ArrayNode.class);
    }

    private void writeJsonFile(String filePath, ArrayNode jsonContent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), jsonContent);
    }

    static class NameAttributes {
        private final String name;
        private final String gender;
        private final String meaning;
        private final String religion;

        public NameAttributes(String name, String gender, String meaning, String religion) {
            this.name = name;
            this.gender = gender;
            this.meaning = meaning;
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

        public String getReligion() {
            return religion;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            NameAttributes that = (NameAttributes) obj;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
