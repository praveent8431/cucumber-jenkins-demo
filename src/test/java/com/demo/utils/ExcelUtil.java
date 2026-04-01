package com.demo.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static List<String[]> readExcel(String filePath, String sheetName) {

        List<String[]> data = new ArrayList<>();

        try (InputStream is =
                     ExcelUtil.class
                             .getClassLoader()
                             .getResourceAsStream(filePath)) {

            if (is == null) {
                throw new RuntimeException(
                        "Excel file not found in classpath: " + filePath);
            }

            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            // ✅ Start from row 1 (skip header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell userCell = row.getCell(0);
                Cell passCell = row.getCell(1);

                if (userCell == null || passCell == null) continue;

                String username = row.getCell(0).getStringCellValue().trim();
                String password = row.getCell(1).getStringCellValue().trim();
                String expected = row.getCell(2).getStringCellValue().trim();

                data.add(new String[]{username, password, expected});
            }

            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file", e);
        }

        return data;
    }
}