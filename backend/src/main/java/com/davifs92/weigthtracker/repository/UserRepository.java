package com.davifs92.weigthtracker.repository;

import com.davifs92.weigthtracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
