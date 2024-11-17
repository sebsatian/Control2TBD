package com.example.Control2TBD.persistence.repositories;

import com.example.Control2TBD.persistence.entities.UserEntity;


public interface UserRepository {
    // DEFAULT
    UserEntity getByUsername(String username);
    void saveUser(UserEntity User);
}
