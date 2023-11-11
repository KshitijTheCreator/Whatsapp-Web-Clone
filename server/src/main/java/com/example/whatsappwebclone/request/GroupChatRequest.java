package com.example.whatsappwebclone.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatRequest {
    private List<Integer> userIds;
    private String chat_name;
    private String chat_image;
}
