package com.jmr.usercenter.config;

import com.jmr.usercenter.controller.message.WebSocketServer;
import com.jmr.usercenter.service.message.MessageService;
import com.jmr.usercenter.utils.UUIDOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setMessageService(MessageService messageService, UUIDOperator uuidOperator) {
        WebSocketServer.messageService = messageService;
        WebSocketServer.uuidOperator = uuidOperator;
    }
}
