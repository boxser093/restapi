package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.dto.UserDto;
import com.ilya.restapiapp.mappers.UserMapper;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.service.impl.UserServiceImp;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.ilya.restapiapp.util.GsonUtils.*;
import static com.ilya.restapiapp.util.RequestUtils.*;


@WebServlet(name = "UserRestControllerV1", urlPatterns = "/api/v1/users/**")
public class UserRestControllerV1 extends HttpServlet {
    private UserServiceImp userServiceImp;
    private UserMapper userMapper;

    public void destroy() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        userServiceImp = new UserServiceImp();
        userMapper = new UserMapper();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        userServiceImp = new UserServiceImp();
        super.init(config);
    }
    @Operation(
            summary = "User Get By Id",
            description = "Get User by ID",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            required = true,
                            description = "ID of Users",
                            schema = @Schema(
                                    defaultValue = "1",
                                    minimum = "1",
                                    allOf = {Integer.class}
                            ),
                            style = ParameterStyle.SIMPLE
                    )
            }
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String getBody = req.getPathInfo();
        if (getBody.contains("all")) {
            List<User> users = userServiceImp.getAll();
            List<UserDto> userDtos = userMapper.map(users);
            writer.print(toJSON(userDtos));
        } else {
            Integer idUser = Integer.valueOf(req.getPathInfo().replace("/", ""));
            User user = userServiceImp.getById(idUser);
            UserDto dto = userMapper.map(user);
            writer.print(toJSON(dto));
        }

    }

    @Operation(
            summary = "Create New User",
            description = "Create a New User for you upload files"
    )
    @RequestBody(
            description = "Нужно передать имя пользователя, в теле запроса",
            required = true,
            content = @Content(mediaType = "application/json",
            schema = @Schema(description = "input name for new User",
            example = "'name':'Petr'"))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        String body = getBody(req);
        User user = fromJson(body, User.class);
        UserDto userDto = userMapper.map(userServiceImp.create(user));
        writer.print(toJSON(userDto));
    }
    @Operation(
            summary = "Update User",
            description = "Update username for User for you upload files"
    )
    @RequestBody(
            description = "Input name and id for user ",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String body = getBody(req);
        User user = fromJson(body, User.class);
        UserDto userDto = userMapper.map(userServiceImp.update(user));
        writer.print(toJSON(userDto));
    }
    @Operation(
            summary = "Delete User by Id",
            description = "Delete User by ID",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            required = true,
                            description = "ID of Users",
                            schema = @Schema(
                                    defaultValue = "1",
                                    minimum = "1",
                                    allOf = {Integer.class}
                            ),
                            style = ParameterStyle.SIMPLE
                    )
            }
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Integer idUser = Integer.valueOf(req.getPathInfo().replace("/", ""));
        boolean resul = userServiceImp.deleteById(idUser);
        if (resul == true) {
            writer.print("User with id:" + idUser + " successful");
        } else {
            writer.print("User not deleted");
        }
    }
}
