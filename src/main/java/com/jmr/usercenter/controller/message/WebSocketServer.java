package com.jmr.usercenter.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.jmr.usercenter.auth.CheckLogin;
import com.jmr.usercenter.domain.dto.websocket.MessageRequestDTO;
import com.jmr.usercenter.domain.entity.message.Message;
import com.jmr.usercenter.domain.entity.message_text.MessageText;
import com.jmr.usercenter.service.message.MessageService;
import com.jmr.usercenter.utils.ApplicationContextRegister;
import com.jmr.usercenter.utils.UUIDOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint("/message/{userId}")
@CheckLogin
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketServer {

    public static MessageService messageService;
    public static UUIDOperator uuidOperator;
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收userId
     */
    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            //加入set中
        } else {
            webSocketMap.put(userId, this); // 加入set中
            addOnlineCount(); // 在线数加1
        }

        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param messageString 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String messageString, Session session) throws IOException {
        log.info("用户:" + userId + ",报文:" + messageString);
        session.getBasicRemote().sendText("服务器接受到消息");


        MessageRequestDTO messageRequestDTO = JSONObject.parseObject(messageString, MessageRequestDTO.class);
        String messageTitle = messageRequestDTO.getMessageTitle();
        String messageContent = messageRequestDTO.getMessageContent();
        List<String> mentions = messageRequestDTO.getMentions();



        String messageTextId = uuidOperator.getUUid();
        MessageText messageText = MessageText.builder()
                .pkId(messageTextId)
                .title(messageTitle)
                .content(messageContent)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        messageService.insertMessageText(messageText);

        for (String receiverId : mentions) {
            Message message = Message.builder()
                    .pkId(uuidOperator.getUUid())
                    .receiverId(receiverId)
                    .senderId(this.userId)
                    .messageTextId(messageTextId)
                    .type(messageRequestDTO.getType())
                    .senderRole(1)
                    .status(false)
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            messageService.insertMessage(message);
            sendInfo(messageString, receiverId);
        }

        // 可以群发消息
        // 消息保存到数据库、redis
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (!userId.equals("") && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
