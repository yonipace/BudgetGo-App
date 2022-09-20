package app.core.util.xlsx;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
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
    public void createHeaderRow() {

        //this is called to get the names of the fields in the entity
        Field[] fields = list.get(0).getClass().getDeclaredFields();

        Row row = sheet.createRow(0);
        int cellNumber = 0;

        for (Field field : fields) {
            createCell(row, cellNumber++, field.getName());
        }
    }

    //create table rows
    public void createTableRows() {
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
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

    }

    public void writeToExcel(String fileName) throws IOException {

        System.out.println("ready to write");
        FileOutputStream out = new FileOutputStream(
                new File("C:/Users/Yonathan/Documents/JB-Java/" + fileName + ".xlsx"));

        workbook.write(out);
        out.close();
    }
}
