package com.example.whatsappwebclone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String message;
    private Boolean is_seen;
    private LocalDateTime timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
