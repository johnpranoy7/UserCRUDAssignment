package com.jyalla.demo.messaging;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("rabbitmq")
public class MQYamlConfig {
    public String queue;
    public String queue2;
    public String exchange;
    public String router;
    public String routerDuplicate;

    public MQYamlConfig(String queue, String queue2, String routerDuplicate, String exchange, String router) {
        super();
        this.queue = queue;
        this.queue2 = queue2;
        this.exchange = exchange;
        this.router = router;
        this.routerDuplicate = routerDuplicate;
    }

    public MQYamlConfig() {
        super();
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public String getQueue2() {
        return queue2;
    }

    public void setQueue2(String queue2) {
        this.queue2 = queue2;
    }

    public String getRouterDuplicate() {
        return routerDuplicate;
    }

    public void setRouterDuplicate(String routerDuplicate) {
        this.routerDuplicate = routerDuplicate;
    }

    @Override
    public String toString() {
        return "MQYamlConfig [queue=" + queue + ", queue2=" + queue2 + ", exchange=" + exchange + ", router=" + router + ", routerDuplicate=" + routerDuplicate + "]";
    }



}
