package com.jmr.usercenter.domain.dto.message;

import com.jmr.usercenter.domain.entity.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageListByReceiverDTO {
    private List<Map<String, Object>> list;
    private Integer total;
    private Integer notRead;
}
