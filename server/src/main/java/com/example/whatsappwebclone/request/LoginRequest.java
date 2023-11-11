package com.example.whatsappwebclone.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    private String email;
    private String password;
}
