package com.example.whatsappwebclone.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private String message;
    private boolean status;
}
