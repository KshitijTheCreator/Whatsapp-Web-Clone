package com.example.whatsappwebclone.DTO;

import com.example.whatsappwebclone.model.Message;
import com.example.whatsappwebclone.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Integer id;
    private String chat_name;
    private String chat_image;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_group;
    @JsonBackReference
    private ChatDTO created_by;

    private Set<UserDTO> admins = new HashSet<>();
    private Set<UserDTO> users = new HashSet<>();
    private List<MessageDTO> messages= new ArrayList<>();
}
