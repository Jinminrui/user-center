package com.jmr.usercenter.controller.message;

import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.message.GetMessageListByReceiverDTO;
import com.jmr.usercenter.domain.dto.message.MessageDTO;
import com.jmr.usercenter.domain.entity.message.Message;
import com.jmr.usercenter.domain.entity.message_text.MessageText;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.service.message.MessageService;
import com.jmr.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("/getMessageListByReceiver")
    public CommonResponseDTO<Object> getMessageListByReceiver(@RequestParam(name = "receiverId") String receiverId) {
        User receiver = userService.findById(receiverId);
        List<Message> messages = messageService.getMessageListByReceiver(receiver.getUsername());
        List<MessageDTO> list = new ArrayList<MessageDTO>();
        for (Message message : messages) {
            MessageText messageText = messageService.getMessageTextById(message.getMessageTextId());
            MessageDTO messageDTO = MessageDTO.builder()
                    .messageId(message.getPkId())
                    .sender(message.getSenderId())
                    .type(message.getType())
                    .receiver(message.getReceiverId())
                    .title(messageText.getTitle())
                    .content(messageText.getContent())
                    .sendTime(message.getCreateTime())
                    .status(message.getStatus())
                    .build();
            list.add(messageDTO);
        }
        Integer total = messageService.getTotal(receiver.getUsername());
        Integer notRead = messageService.getUnReadNum(receiver.getUsername());
        return CommonResponseDTO.builder().code(200).data(GetMessageListByReceiverDTO.builder()
                .list(list)
                .total(total)
                .notRead(notRead)
                .build()
        ).desc("success").build();
    }
}
