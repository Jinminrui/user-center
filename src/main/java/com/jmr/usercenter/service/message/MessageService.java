package com.jmr.usercenter.service.message;

import com.jmr.usercenter.dao.message.MessageMapper;
import com.jmr.usercenter.dao.message_text.MessageTextMapper;
import com.jmr.usercenter.domain.entity.message.Message;
import com.jmr.usercenter.domain.entity.message_text.MessageText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageService {
    private final MessageMapper messageMapper;
    private final MessageTextMapper messageTextMapper;

    public void insertMessageText(MessageText messageText) {
        messageTextMapper.insert(messageText);
    }

    public void insertMessage(Message message) {
        messageMapper.insert(message);
    }
}
