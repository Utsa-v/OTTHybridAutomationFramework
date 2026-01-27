package com.binge.qa.utils.Utility;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class UtilityQA{


    public static Object[][] getTestDataFromExcel(String sheetName) {

        Object[][] data = null;

        try {
            File xlsx = new File(System.getProperty("user.dir") +
                    "/src/main/java/com/binge/qa/testData/ProviderAutosuggestion.xlsx");

            FileInputStream fis = new FileInputStream(xlsx);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getLastRowNum(); // excludes header
            int colCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][colCount];

            for (int i = 0; i < rowCount; i++) {
                XSSFRow row = sheet.getRow(i + 1); // skip header

                for (int j = 0; j < colCount; j++) {

                    XSSFCell cell = row.getCell(j);

                    if (cell == null) {
                        data[i][j] = "";
                        continue;
                    }

                    switch (cell.getCellType()) {
                        case STRING:
                            data[i][j] = cell.getStringCellValue();
                            break;

                        case NUMERIC:
                            data[i][j] = String.valueOf((long) cell.getNumericCellValue());
                            break;

                        default:
                            data[i][j] = "";
                    }
                }
            }

            workbook.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static String getRandomString(int length) {
            String chars = "abcdefghijklmnopqrstuvwxyz";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < length; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return sb.toString();
        }

    public static String getTheScreenShot(WebDriver driver, String testName){
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            String screenshotPath =
                    System.getProperty("user.dir")
                            + "/Screenshot/"
                            + testName + "_" + timestamp + ".png";

            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(screenshotPath);

            FileUtils.copyFile(src, dest);

            return screenshotPath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}




