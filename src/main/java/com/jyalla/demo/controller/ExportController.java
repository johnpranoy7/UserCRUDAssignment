package com.jyalla.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;
import com.jyalla.demo.util.ExcelUtil;
import com.jyalla.demo.util.PdfUtil;

@RestController
public class ExportController {

    @Autowired
    UserService userService;

    @Autowired
    ExcelUtil excelUtil;

    @Autowired
    PdfUtil pdfUtil;

    @GetMapping("/users-excel")
    public ResponseEntity<InputStreamResource> getUsersExcel() throws IOException {
        List<User> data = userService.getAllUsers();
        ByteArrayInputStream resource = excelUtil.createAndExport(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=roles.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(resource));
    }

    @GetMapping("/users-pdf")
    public ResponseEntity<InputStreamResource> getUsersPdf() throws IOException {
        List<User> data = userService.getAllUsers();
        ByteArrayInputStream resource = pdfUtil.createPdf(data);
        var path = "./src/main/resources/SimpleTable.pdf";
        File file = new File(path);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=roles.pdf")
                .body(new InputStreamResource(resource));
    }

}
