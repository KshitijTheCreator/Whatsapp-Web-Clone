package com.example.whatsappwebclone.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class SendMessageRequest {
    private Integer userId;
    private Integer chatId;
    private String content;
}
