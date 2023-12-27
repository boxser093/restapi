package com.ilya.restapiapp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilya.restapiapp.conntectbd.HibernateStatementFactory;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.repository.UserRepository;
import com.ilya.restapiapp.repository.impl.HibUserRepImpl;
import com.ilya.restapiapp.service.UserService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class UserServiceImp implements UserService {
//    private final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @Getter
    private HibUserRepImpl userRepository;
    private String loggMessage;
    private Gson gson;

    public UserServiceImp() {
        this.gson = new Gson();
//        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.userRepository = new HibUserRepImpl();
    }

    @Override
    public User getById(Integer id) {
        User result = userRepository.getById(id);
        return result;
    }

    @Override
    public User create(User user) {
        User save = userRepository.save(user);
//        loggMessage = String.format("## User Service ## get By id(%s), name(%s)", save.getId(), save.getName());
//        logger.info(loggMessage);
        return save;
    }


    @Override
    public boolean deleteById(Integer id) {
        boolean result = userRepository.deleteById(id);
//        loggMessage = "## User Service ## delete User: " + result;
//        logger.info(loggMessage);
        return result;
    }

    @Override
    public User update(User user) {
        user = userRepository.update(user);
//        loggMessage = String.format("## User Service ## update User: id(%s),old name(%s), new name(%s)",
//                user.getId(), user.getName(), user.getName());
//        logger.info(loggMessage);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.getAll();
        return result;
    }

}
