package com.example.whatsappwebclone.request;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String profilePicture;
}
