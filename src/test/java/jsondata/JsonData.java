package jsondata;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class JsonData {

    @Test
    void jsonParseData() throws IOException, ParseException {

        FileReader fileReader = new FileReader("./src/test/resources/2147478103_zoom_hindi.json");
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(fileReader);
        System.out.println("JSONObject: " + object);

        JSONArray childrenArray = (JSONArray) ((JSONObject) object.get("response")).get("data");
        System.out.println("JSONArray (Children): " + childrenArray);

        writeToExcel(childrenArray);

        System.out.println("File Read and Excel created");
    }

    private void writeToExcel(JSONArray jsonArray) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TitleAndSeopath");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Seopath");

        extractTitleAndSeopath(jsonArray, sheet, 1);

        try (FileOutputStream fileOut = new FileOutputStream("./src/test/resources/ZoomHindiTitleAndSeopath.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int extractTitleAndSeopath(JSONArray jsonArray, Sheet sheet, int rowIndex) {
        for (Object item : jsonArray) {
            if (item instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) item;
                String seopath = (String) jsonObject.get("seopath");
                String title = (String) jsonObject.get("title");

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(title);
                row.createCell(1).setCellValue(seopath);

                System.out.println("Title: " + title);
                System.out.println("Seopath: " + seopath);
                System.out.println("----------------------------------------------");

                JSONArray nestedChildren = (JSONArray) jsonObject.get("children");
                if (nestedChildren != null) {
                    rowIndex = extractTitleAndSeopath(nestedChildren, sheet, rowIndex);
                }
            }
        }
        return rowIndex;
    }
}
