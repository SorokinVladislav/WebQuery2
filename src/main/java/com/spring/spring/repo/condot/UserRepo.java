package com.spring.spring.repo.condot;

import com.spring.spring.models.condot.tbl_users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<tbl_users, Long> {
    tbl_users findByUsername(String username);
}