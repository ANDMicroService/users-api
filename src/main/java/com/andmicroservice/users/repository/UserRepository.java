package com.andmicroservice.users.repository;

import com.andmicroservice.users.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAll();

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneByEmail(String email);
}
