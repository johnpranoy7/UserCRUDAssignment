package com.jyalla.demo.util;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;
import com.jyalla.demo.modal.User;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;

@Component
public class PdfUtil {


    public ByteArrayInputStream createPdf(List<User> data) throws IOException {
        String outputFileName = "./src/main/resources/SimpleTable.pdf";

        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDRectangle rect = page.getMediaBox();
        document.addPage(page);

        PDPageContentStream cos = new PDPageContentStream(document, page);

        float margin = 50;
        float yStartNewPage = page.getMediaBox()
                .getHeight() - (2 * margin);
        float tableWidth = page.getMediaBox()
                .getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        float yPosition = 550;

        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
        Row<PDPage> headerRow = table.createRow(50);
        Cell<PDPage> cell = headerRow.createCell(100, "User Data");
        cell.setFont(fontBold);
        cell.setFontSize(20);
        cell.setValign(VerticalAlignment.MIDDLE);
        cell.setAlign(HorizontalAlignment.CENTER);
        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
        table.addHeaderRow(headerRow);

        Row<PDPage> row = table.createRow(20);
        cell = row.createCell(14.285f, "ID");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(14.285f, "Username");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(14.285f, "Mail");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(14.285f, "PhoneNo");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(14.285f, "ProfilePic");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(14.285f, "Role");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(14.285f, "Status");
        cell.setFontSize(10);
        cell.setFont(fontBold);


        for (User user : data) {
            row = table.createRow(20);

            cell = row.createCell(14.285f, String.valueOf(user.getId()));
            cell.setTextColor(Color.GREEN);
            cell.setFontSize(10);
            cell.setFont(fontItalic);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = row.createCell(14.285f, String.valueOf(user.getUsername()));
            cell.setTextColor(Color.GREEN);
            cell.setFontSize(10);
            cell.setFont(fontItalic);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = row.createCell(14.285f, String.valueOf(user.getEmail()));
            cell.setTextColor(Color.GREEN);
            cell.setFontSize(10);
            cell.setFont(fontItalic);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = row.createCell(14.285f, String.valueOf(user.getPhoneNo()));
            cell.setTextColor(Color.GREEN);
            cell.setFontSize(10);
            cell.setFont(fontItalic);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = row.createCell(14.285f, String.valueOf(user.getProfilePic()));
            cell.setTextColor(Color.GREEN);
            cell.setFontSize(10);
            cell.setFont(fontItalic);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = row.createCell(14.285f, String.valueOf(user.getRole()));
            cell.setTextColor(Color.GREEN);
            cell.setFontSize(10);
            cell.setFont(fontItalic);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = row.createCell(14.285f, String.valueOf(user.getStatus()));
            cell.setTextColor(Color.GREEN);
            cell.setFontSize(10);
            cell.setFont(fontItalic);
            cell.setAlign(HorizontalAlignment.CENTER);
        }

        table.draw();

        cos.close();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        document.close();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return in;
    }

}
