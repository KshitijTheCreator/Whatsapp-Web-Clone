package com.example.whatsappwebclone.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SendMessageRequest {
    private Integer userId;
    private Integer chatId;
    private String content;
}
