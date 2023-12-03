package com.example.chatIslem.repositoies;

import com.example.chatIslem.models.user.ERole;
import com.example.chatIslem.models.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    @Query("{'name': ?0}")
    Optional<Role> findByName(ERole name);
}
