package app.core.util.xlsx;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class WriteExcelDemo {

    public static void main(String[] args) {

        //empty workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //empty spreadsheet
        XSSFSheet sheet = workbook.createSheet("sheet 1");

        //list of objects to be added to the spreadsheet
        List<Person> data = List.of(new Person(1, "yoni", 31),
                new Person(2, "mike", 22),
                new Person(3, "leah", 27));

        //Iterate over data and write to sheet
        int rowNumber = 0;
        //iterate over each list item and assign it to a new row
        for (Person person : data) {
            Row row = sheet.createRow(rowNumber++);
            //iterate over each field and assign ot to a cell
            int cellNumber = 0;
        }


    }

}
