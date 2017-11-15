package com.andmicroservice.users.service;

import com.andmicroservice.users.domain.User;
import com.andmicroservice.users.exception.EmailExistsException;
import com.andmicroservice.users.exception.IdProvidedException;
import com.andmicroservice.users.exception.LoginExistsException;
import com.andmicroservice.users.repository.UserRepository;
import com.andmicroservice.users.representation.UserDTO;
import com.andmicroservice.users.representation.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<com.andmicroservice.users.domain.User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws EmailExistsException, LoginExistsException, IdProvidedException {
        logger.info("Creating user: {}", userDTO);

        if(userDTO.getId() != null) {
            throw new IdProvidedException();
        } else if (userRepository.findOneByLogin(userDTO.getLogin()).isPresent()) {
            throw new LoginExistsException();
        } else if (userRepository.findOneByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailExistsException();
        }

        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);

        return userMapper.map(user);
    }

    @Override
    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            logger.debug("Deleted User: {}", user);
        });
    }
}
