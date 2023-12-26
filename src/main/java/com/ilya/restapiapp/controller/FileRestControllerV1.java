package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.model.File;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.service.impl.EventServiceImp;
import com.ilya.restapiapp.service.impl.FileServiceImp;
import com.ilya.restapiapp.service.impl.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FileRestControllerV1", urlPatterns = "/api/v1/files**")
public class FileRestControllerV1 extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(FileRestControllerV1.class);
    private String loggerMessage;
    private FileServiceImp fileService = new FileServiceImp();
    private UserServiceImp userService = new UserServiceImp();
    private EventServiceImp eventService = new EventServiceImp();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        File file = fileService.fromJson(fileService.getBody(req));
        file = fileService.getById(file.getId());
        writer.print(fileService.toGson(file));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String fileName = fileService.downLoadFile(req);
        File file = File.builder().name(fileName).filePath(fileService.getFilePath()).build();
        file = fileService.create(file);
        User user = userService.getById(Integer.valueOf(req.getHeader("id")));
        user.getEvents();
        Event event = Event.builder().file(file).user(user).build();
        event = eventService.create(event);
        String body = eventService.toJSON(event);
        writer.print(body);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        File update = fileService.update(fileService.fromJson(fileService.getBody(req)));
        writer.print(fileService.toGson(update));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        File file = fileService.fromJson(fileService.getBody(req));
        boolean result = fileService.deleteById(file.getId());

        writer.print(result);
    }

}
