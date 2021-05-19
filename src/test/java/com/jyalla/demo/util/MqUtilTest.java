package com.jyalla.demo.util;

import static org.assertj.core.api.Assertions.assertThatNoException;
import java.util.Date;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.messaging.CustomMessage;
import com.jyalla.demo.messaging.MQUtil;
import com.jyalla.demo.messaging.MQYamlConfig;


@TestMethodOrder(OrderAnnotation.class)
class MqUtilTest extends BaseClass {

    @InjectMocks
    MQUtil mqUtil;

    @Mock
    RabbitTemplate template;

    @Mock
    MQYamlConfig mqYaml;

    public static Logger logger = LoggerFactory.getLogger(MqUtilTest.class);

    @Test
    @Order(1)
    void publishMessage() {
        CustomMessage message = new CustomMessage("1", "test", new Date());

        mqUtil.publishMessage(message);
        assertThatNoException();
    }

    @Test
    @Order(2)
    void publishMessageMultiple() {
        CustomMessage message = new CustomMessage("1", "testMultiple", new Date());

        mqUtil.publishMessageMultiple(message);
        assertThatNoException();
    }

}
