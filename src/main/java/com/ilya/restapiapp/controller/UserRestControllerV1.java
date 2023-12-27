package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.dto.UserDto;
import com.ilya.restapiapp.mappers.UserMapper;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.service.impl.UserServiceImp;
import com.ilya.restapiapp.util.RequestUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;

import static com.ilya.restapiapp.util.GsonUtils.*;
import static com.ilya.restapiapp.util.RequestUtils.*;

@WebServlet(name = "UserRestControllerV1", urlPatterns = "/api/v1/users/**")
public class UserRestControllerV1 extends HttpServlet {
//    private Logger logger;

    private UserServiceImp userServiceImp;
    private UserMapper userMapper;
    public void destroy() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        userServiceImp = new UserServiceImp();
        userMapper = new UserMapper();
//        logger = LoggerFactory.getLogger(UserRestControllerV1.class);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        userServiceImp = new UserServiceImp();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String getBody = req.getPathInfo();
        if(getBody.contains("all")){
            List<User> users = userServiceImp.getAll();
            List<UserDto> userDtos = userMapper.map(users);
            writer.print(toJSON(userDtos));
        } else {
            Integer idUser = Integer.valueOf(req.getPathInfo().replace("/",""));
            User user = userServiceImp.getById(idUser);
            UserDto dto = userMapper.map(user);
            writer.print(toJSON(dto));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        String body = getBody(req);
        User user = fromJson(body,User.class);
        UserDto userDto = userMapper.map(userServiceImp.create(user));
        writer.print(toJSON(userDto));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String body = getBody(req);
        User user = fromJson(body,User.class);
        UserDto userDto = userMapper.map(userServiceImp.update(user));
        writer.print(toJSON(userDto));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Integer idUser = Integer.valueOf(req.getPathInfo().replace("/", ""));
        boolean resul = userServiceImp.deleteById(idUser);
        if (resul == true) {
            writer.print("User with id:" + idUser + " successful");
        }else {
            writer.print("User not deleted");
        }
    }
}
