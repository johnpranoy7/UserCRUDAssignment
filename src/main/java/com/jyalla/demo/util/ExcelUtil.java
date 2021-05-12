package com.jyalla.demo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import com.jyalla.demo.modal.User;

@Component
public class ExcelUtil {
    public ByteArrayInputStream createAndExport(List<User> data) throws IOException {
        byte[] bytes = new byte[1024];
        Workbook workbook = createReport(data);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private Workbook createReport(List<User> data) {
        Workbook wb = new HSSFWorkbook();
        CreationHelper creationHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("First Sheet");

        Row rowHeading = sheet.createRow(1);

        rowHeading.createCell(1)
                .setCellValue(creationHelper.createRichTextString("UserId"));
        rowHeading.createCell(2)
                .setCellValue(creationHelper.createRichTextString("Username"));
        // rowHeading.createCell(3)
        // .setCellValue(creationHelper.createRichTextString("Password"));
        rowHeading.createCell(4)
                .setCellValue(creationHelper.createRichTextString("Mail"));
        rowHeading.createCell(5)
                .setCellValue(creationHelper.createRichTextString("PhoneNo"));
        rowHeading.createCell(6)
                .setCellValue(creationHelper.createRichTextString("ProfilePic"));
        rowHeading.createCell(7)
                .setCellValue(creationHelper.createRichTextString("Role"));
        rowHeading.createCell(3)
                .setCellValue(creationHelper.createRichTextString("Status"));
        // rowHeading.createCell(9)
        // .setCellValue(creationHelper.createRichTextString("CreatedBy"));
        // rowHeading.createCell(10)
        // .setCellValue(creationHelper.createRichTextString("CreatedOn"));
        // rowHeading.createCell(11)
        // .setCellValue(creationHelper.createRichTextString("UpdatedBy"));
        // rowHeading.createCell(12)
        // .setCellValue(creationHelper.createRichTextString("UpdatedOn"));

        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat()
                .getFormat("dd-mm-yyyy"));
        int i = 2;
        for (User user : data) {
            Row row = sheet.createRow(i++);
            row.createCell(1)
                    .setCellValue(creationHelper.createRichTextString(user.getId()
                            .toString()));
            row.createCell(2)
                    .setCellValue(creationHelper.createRichTextString(user.getUsername()));
            // row.createCell(3)
            // .setCellValue(creationHelper.createRichTextString(user.getPassword()));
            row.createCell(4)
                    .setCellValue(creationHelper.createRichTextString(user.getEmail()));
            row.createCell(5)
                    .setCellValue(creationHelper.createRichTextString(user.getPhoneNo()));
            row.createCell(6)
                    .setCellValue(creationHelper.createRichTextString(user.getProfilePic()));
            row.createCell(7)
                    .setCellValue(user.getRole());
            row.createCell(3)
                    .setCellValue(user.getStatus());
            // row.createCell(9)
            // .setCellValue(creationHelper.createRichTextString(user.getCreatedBy()));
            // Cell cell = row.createCell(10);
            // cell.setCellValue(user.getCreatedOn());
            // cell.setCellStyle(dateStyle);
            //
            // row.createCell(11)
            // .setCellValue(creationHelper.createRichTextString(user.getUpdatedBy()));
            // cell = row.createCell(12);
            // cell.setCellStyle(dateStyle);
            // cell.setCellValue(user.getUpdatedOn());
        }

        return wb;
    }
}
