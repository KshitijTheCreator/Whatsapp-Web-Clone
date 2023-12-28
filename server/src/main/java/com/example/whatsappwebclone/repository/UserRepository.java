package com.example.whatsappwebclone.repository;

import com.example.whatsappwebclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);

    @Query("select u from User u where u.full_name like %:query")
    public List<User> searchUser(@Param("query") String query);

}
