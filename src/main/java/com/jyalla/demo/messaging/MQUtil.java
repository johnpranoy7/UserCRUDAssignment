package com.jyalla.demo.messaging;

import java.util.Date;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQUtil {

    @Autowired
    RabbitTemplate template;

    @Autowired
    MQYamlConfig mqYaml;

    public boolean publishMessage(CustomMessage message) {

        message.setMessageId(UUID.randomUUID()
                .toString());
        message.setMessageDate(new Date());
        template.convertAndSend(mqYaml.exchange, mqYaml.router, message);

        return true;
    }

    public boolean publishMessageMultiple(CustomMessage message) {

        message.setMessageId(UUID.randomUUID()
                .toString());
        message.setMessageDate(new Date());
        template.convertAndSend(mqYaml.exchange, mqYaml.routerDuplicate, message);
        return true;
    }

}
