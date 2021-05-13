package com.jyalla.demo.util;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.modal.JwtRequest;
import com.jyalla.demo.service.UserService;


@TestMethodOrder(OrderAnnotation.class)
class PDFUtilTest extends BaseClass {

    @InjectMocks
    PdfUtil pdfUtil;

    @InjectMocks
    JwtUtil jwtUtil;

    @Mock
    UserService userService;

    public static Logger logger = LoggerFactory.getLogger(PDFUtilTest.class);
    static String token;
    private String USER_NAME = "john";

    @Test
    @Order(1)
    void getStringTokenUtil() {
        jwtUtil.setSecret("evoke");
        jwtUtil.setMaxExpiry(500000000);
        JwtRequest req = new JwtRequest(USER_NAME, USER_NAME);
        UserDetails user = new User(req.getUsername(), req.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        when(userService.findByUsername(Mockito.anyString())).thenReturn(new com.jyalla.demo.modal.User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser",
                "dummy@email.com", "1234", "", true, "Admin", new Date(), "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2));
        token = jwtUtil.generateToken(user);
        logger.info(token);
        assertNotNull(token);
    }

    @Test
    @Order(2)
    void generatePdf() throws IOException {
        ByteArrayInputStream export = pdfUtil.createPdf(List.of(new com.jyalla.demo.modal.User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser",
                "dummy@email.com", "1234", "", true, "Admin", new Date(), "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2)));

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = export.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        // StandardCharsets.UTF_8.name() > JDK 7
        String res = result.toString("UTF-8");

        logger.info("export data: {}", res);
        assertNotNull(export);
    }



}
