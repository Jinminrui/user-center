package com.jmr.usercenter.schedule;

import com.jmr.usercenter.controller.message.WebSocketServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketScheduleTask {

    private final WebSocketServer webSocketServer;


    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=5*1000)
    private void configureTasks() throws Exception{
        webSocketServer.sendMessage("连接成功");
    }
}