package com.example.whatsappwebclone.DTO;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Integer id;
    private String content;
    private LocalDateTime timeStamp;

    @ManyToOne // User can make many messages, but a single message will be created by only one user.
    private UserDTO user;
    private ChatDTO chat;
}
