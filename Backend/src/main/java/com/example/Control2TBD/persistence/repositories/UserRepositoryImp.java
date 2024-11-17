package com.example.Control2TBD.persistence.repositories;

import com.example.Control2TBD.persistence.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import java.lang.String;
import java.util.List;

@Repository
public class UserRepositoryImp implements UserRepository{
    @Autowired
    private Sql2o sql2o;

    // DEFAULT ------------------------------------------------------------------------------------


    @Override
    public UserEntity getByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("username",username)
                    .executeAndFetchFirst(UserEntity.class);
        }
    }

    @Override
    public void saveUser(UserEntity user) {
        String sql = "INSERT INTO users (username, password) VALUES (:username, :password)";
        try (org.sql2o.Connection con = sql2o.beginTransaction()) { // Usar una transacción explícita
            con.createQuery(sql)
                    .addParameter("username", user.getUsername())
                    .addParameter("password", user.getPassword())
                    .executeUpdate();
            con.commit(); // Confirma la transacción
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el usuario", e);
        }
    }

}
