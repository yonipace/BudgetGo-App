package app.core.util.xlsx;

import app.core.entities.Trip;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractExcelWriter<T> {

    //entity list
    private List<T> list;
    //new workbook
    private XSSFWorkbook workbook;
    //new sheet
    private XSSFSheet sheet;

    // header row style
//    CellStyle cellStyle = workbook.createCellStyle();
//    XSSFFont font = workbook.createFont();


    //CTOR that receives a List of <T> and instantiates the workbook
    public AbstractExcelWriter(List<T> list) {

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(list.get(0).getClass().getName());
        this.list = list;
    }

    //create header row
    private void createHeaderRow() {

        //this is called to get the names of the fields in the entity
        Field[] fields = list.get(0).getClass().getDeclaredFields();

        Row row = sheet.createRow(0);
        int cellNumber = 0;

        for (Field field : fields) {
            createCell(row, cellNumber++, field.getName());
        }
    }

    //create table rows
    private void createTableRows() {
        AtomicInteger rowCount = new AtomicInteger();
        rowCount.set(1);

        //iterate over each entity
        list.forEach(e ->
        {
            System.out.println(e);
            AtomicInteger cellNumber = new AtomicInteger();
            Row row = sheet.createRow(rowCount.getAndIncrement());

            //iterate over the fields of each entity
            ReflectionUtils.doWithFields(e.getClass(), field -> {
                field.setAccessible(true);
                System.out.println(field.get(e));
                createCell(row, cellNumber.getAndIncrement(), field.get(e));
            });
        });
    }

    private void createCell(Row row, int cellNumber, Object value) {
        Cell cell = row.createCell(cellNumber);
        if (value instanceof Integer) {
            cell.setCellValue((int) value);
        } else if (value instanceof Double) {
            cell.setCellValue((double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Enum) {
            cell.setCellValue((String) value.toString());
        } else if (value instanceof LocalDate) {
            cell.setCellValue((String) value.toString());
        } else if (value instanceof Trip) {
            cell.setCellValue(((Trip) value).getName());
        } else {
            cell.setCellValue((String) value);
        }

    }

    /*this method is used to create an Excel file and save it to the local machine*/
    public String writeToExcel(String path, String fileName) throws IOException {

        createHeaderRow();
        createTableRows();

        System.out.println("ready to write");
        String filePath = path + "/" + fileName + ".xlsx";
        FileOutputStream out = new FileOutputStream(
                new File(filePath));

        workbook.write(out);
        out.close();
        return filePath;
    }

    /*this method is used to send the created workbook back as a bytestream, to later be sent to the user via HTTP*/
    public ByteArrayInputStream loadWorkBook() throws IOException {

        createHeaderRow();
        createTableRows();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());

    }


}
