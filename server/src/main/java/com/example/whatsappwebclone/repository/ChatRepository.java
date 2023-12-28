package com.example.whatsappwebclone.repository;

import com.example.whatsappwebclone.model.Chat;
import com.example.whatsappwebclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query("select c from Chat c join c.users u where u.id = :userId")
    public List<Chat> findChatByUserId(Integer userId);
    @Query("select c from Chat c where c.is_group = false and :user member of c.users and :reqUser member of c.users")
    public Chat findSingleChatByUserIds(@Param("user")User user, @Param("reqUser") User reqUser);
}
