package com.example.whatsappwebclone.request;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String full_name;
    private String profile_picture;
}
