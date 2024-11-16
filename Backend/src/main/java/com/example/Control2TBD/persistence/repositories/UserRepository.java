package com.example.Control2TBD.persistence.repositories;

import com.example.Control2TBD.persistence.entities.UserEntity;

import java.util.List;

public interface UserRepository {
    // DEFAULT
    List<UserEntity> getAll();
    UserEntity getById(Long id);
    UserEntity getByUsername(String username);
    void saveUser(UserEntity User);
}
