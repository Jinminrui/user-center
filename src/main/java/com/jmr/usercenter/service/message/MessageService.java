package com.jmr.usercenter.service.message;

import com.jmr.usercenter.dao.message.MessageMapper;
import com.jmr.usercenter.dao.message_text.MessageTextMapper;
import com.jmr.usercenter.domain.entity.message.Message;
import com.jmr.usercenter.domain.entity.message_text.MessageText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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

    public Integer getTotal(String receiver) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverId", receiver);
        return messageMapper.selectCountByExample(example);
    }

    public Integer getUnReadNum(String receiver) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverId", receiver);
        criteria.andEqualTo("status", false);
        return messageMapper.selectCountByExample(example);
    }

    public MessageText getMessageTextById(String id) {
        return messageTextMapper.selectByPrimaryKey(id);
    }

    public List<Message> getMessageListByReceiver(String receiverId) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverId", receiverId);
        return messageMapper.selectByExample(example);
    }

    public List<Message> getMessageListBySender(String senderId) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("senderId", senderId);
        return messageMapper.selectByExample(example);
    }
}
