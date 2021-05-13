package com.jyalla.demo.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;
import com.jyalla.demo.util.ExcelUtil;
import com.jyalla.demo.util.PdfUtil;


@TestMethodOrder(OrderAnnotation.class)
class ExportControllerMockTest extends BaseClass {


    public static Logger logger = LoggerFactory.getLogger(ExportControllerMockTest.class);

    @InjectMocks
    ExportController exportController;

    @Mock
    UserService userService;

    @Mock
    ExcelUtil excelUtil;

    @Mock
    PdfUtil pdfUtil;

    @Test
    @Order(1)
    void getUsersExcel() throws IOException {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        User user2 = new User(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "dummyUser2", "dummy2@email.com", "4321", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        when(userService.getAllUsers()).thenReturn(List.of(user, user2));
        when(excelUtil.createAndExport(Mockito.any())).thenReturn(new ByteArrayInputStream("hello".getBytes()));
        ResponseEntity<InputStreamResource> users = exportController.getUsersExcel();
        logger.info("getUsers {}", users);
        assertTrue(users.getStatusCode()
                .is2xxSuccessful());
        assertNotNull(users.getBody());
    }

    @Test
    @Order(2)
    void getUsersPdf() throws IOException {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        User user2 = new User(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "dummyUser2", "dummy2@email.com", "4321", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        when(userService.getAllUsers()).thenReturn(List.of(user, user2));
        when(pdfUtil.createPdf(Mockito.any())).thenReturn(new ByteArrayInputStream("hello".getBytes()));
        ResponseEntity<InputStreamResource> users = exportController.getUsersPdf();
        logger.info("getUsers {}", users);
        assertTrue(users.getStatusCode()
                .is2xxSuccessful());
        assertNotNull(users.getBody());
    }

}
