package com.jmr.usercenter.service.message;

import com.jmr.usercenter.dao.message.MessageMapper;
import com.jmr.usercenter.domain.entity.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageService {
    private final MessageMapper messageMapper;

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

    public List<Map<String,Object>> getMessageListByReceiver(String receiverId) {
       return messageMapper.getMessageListByReceiver(receiverId);
    }

    public List<Message> getMessageListBySender(String senderId) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("senderId", senderId);
        return messageMapper.selectByExample(example);
    }

    public void deleteMessage(String receiver, Integer type){
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverId", receiver);
        criteria.andEqualTo("type", type);
        messageMapper.deleteByExample(example);
    }

    public int deleteOneMessage(String id) {
        return messageMapper.deleteByPrimaryKey(id);
    }

    public void readOneMessage(String messageId) {
        Message message = new Message();
        message.setPkId(messageId);
        message.setStatus(true);
        messageMapper.updateByPrimaryKeySelective(message);
    }
}
