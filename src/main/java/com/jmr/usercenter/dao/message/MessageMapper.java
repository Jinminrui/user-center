package com.jmr.usercenter.dao.message;

import com.jmr.usercenter.domain.entity.message.Message;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface MessageMapper extends Mapper<Message> {
    List<Map<String, Object>> getMessageListByReceiver (@Param("receiverId") String receiverId);
}