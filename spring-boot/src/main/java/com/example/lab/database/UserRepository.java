package com.example.lab.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
