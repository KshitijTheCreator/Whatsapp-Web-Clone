package com.example.whatsappwebclone.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String full_name;
    private String email;
    private String profile_picture;
}
