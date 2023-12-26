package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.service.impl.UserServiceImp;
import lombok.Getter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserRestControllerV1", urlPatterns = "/api/v1/users/**")
public class UserRestControllerV1 extends HttpServlet {
//    private Logger logger = LoggerFactory.getLogger(UserRestControllerV1.class);
    @Getter
    private UserServiceImp userServiceImp;

    @Override
    public void destroy() {
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
        String string = req.getContextPath();
        String string1 = req.getRequestURI();
        String string2 = req.getRequestURL().toString();
        String string3 = req.getQueryString();


//        user = userServiceImp.getById();
//        writer.print(userServiceImp.toJSON(user));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = userServiceImp.getBody(req);
        PrintWriter writer = resp.getWriter();
        writer.print(body + "\n");
        User user = userServiceImp.fromJson(body);
        User result = userServiceImp.create(user);
        writer.print(userServiceImp.toJSON(result));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String body = userServiceImp.getBody(req);
        User update = userServiceImp.update(userServiceImp.fromJson(body));
        writer.print(userServiceImp.toJSON(update));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        User user = userServiceImp.fromJson(userServiceImp.getBody(req));
        PrintWriter writer = resp.getWriter();
        boolean result = userServiceImp.deleteById(user.getId());
        writer.print(result);

    }
}
