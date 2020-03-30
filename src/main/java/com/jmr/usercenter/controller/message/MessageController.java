package com.jmr.usercenter.controller.message;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.message.DeleteMessageDTO;
import com.jmr.usercenter.domain.dto.message.MessageListByReceiverDTO;
import com.jmr.usercenter.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/getMessageListByReceiver")
    public CommonResponseDTO<MessageListByReceiverDTO> getMessageListByReceiver(@RequestParam(name = "receiverId") String receiverId,
                                                                                @RequestParam(name = "pageSize") int pageSize,
                                                                                @RequestParam(name = "pageNum") int pageNum) {
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> messages = messageService.getMessageListByReceiver(receiverId);
        Integer total = Math.toIntExact(page.getTotal());
        Integer notRead = messageService.getUnReadNum(receiverId);
        return CommonResponseDTO.<MessageListByReceiverDTO>builder().code(200).data(MessageListByReceiverDTO.builder()
                .list(messages)
                .total(total)
                .notRead(notRead)
                .build()
        ).desc("success").build();
    }

    @PostMapping("/deleteMessage")
    public CommonResponseDTO<Object> deleteMessage(@RequestBody DeleteMessageDTO deleteMessageDTO) {
        messageService.deleteMessage(deleteMessageDTO.getReceiver(), deleteMessageDTO.getType());
        return CommonResponseDTO.builder().code(200).desc("删除成功").build();
    }

    @PostMapping("deleteOneMessage/{id}")
    public void deleteOneMessage(@PathVariable String id) {
        messageService.deleteOneMessage(id);
    }

    @PostMapping("/readOneMessage/{messageId}")
    public CommonResponseDTO<Object> readOneMessage(@PathVariable String messageId) {
        messageService.readOneMessage(messageId);
        return CommonResponseDTO.builder().code(200).desc("更新成功").build();
    }
}
