import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewExcel {
    private static final Workbook workbook = new XSSFWorkbook();
    private static final Sheet sheet = workbook.createSheet("My DataBase");
    private static int currentRow = 0;

    public NewExcel() {
        Row row = sheet.createRow(currentRow++);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("lp");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("Name file");
        Cell cell2 = row.createCell(2);
        cell2.setCellValue("Size file");
        Cell cell3 = row.createCell(3);
        cell3.setCellValue("Hash file");
    }

    public static void writeExcel(File file, int count, String hash) {
        Row row = sheet.createRow(currentRow++); // створюємо новий рядок

        Cell cell0 = row.createCell(0);
        cell0.setCellValue(count);
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(file.getName());
        Cell cell2 = row.createCell(2);
        cell2.setCellValue(file.length());
        Cell cell3 = row.createCell(3);
        cell3.setCellValue(hash);

        try (FileOutputStream outputStream = new FileOutputStream("temp.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeWorkbook() {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
