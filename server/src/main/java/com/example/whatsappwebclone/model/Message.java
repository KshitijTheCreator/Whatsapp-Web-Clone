package com.example.whatsappwebclone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;
    private LocalDateTime timeStamp;

    @ManyToOne // User can make many messages, but a single message will be created by only one user.
    private User user;

    @ManyToOne // Similarly chat can have many messages but a single message will be created for the single chat only.
    private Chat chat;
}
