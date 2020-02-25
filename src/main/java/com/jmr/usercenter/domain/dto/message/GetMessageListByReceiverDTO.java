package com.jmr.usercenter.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GetMessageListByReceiverDTO {
    private List<MessageDTO> list;
    private Integer total;
    private Integer notRead;
}
