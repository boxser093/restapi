package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.dto.EventDto;
import com.ilya.restapiapp.dto.FileDto;
import com.ilya.restapiapp.mappers.FIleMapper;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.model.File;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.service.impl.EventServiceImp;
import com.ilya.restapiapp.service.impl.FileServiceImp;
import com.ilya.restapiapp.service.impl.UserServiceImp;
import com.ilya.restapiapp.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static com.ilya.restapiapp.util.GsonUtils.*;
import static com.ilya.restapiapp.util.RequestUtils.*;


@WebServlet(name = "FileRestControllerV1", urlPatterns = "/api/v1/files**")
public class FileRestControllerV1 extends HttpServlet {
//    private Logger logger;
    private FileServiceImp fileService;
    private UserServiceImp userService;
    private EventServiceImp eventService;
    private FIleMapper mapper;

    @Override
    public void init() throws ServletException {
        super.init();
        fileService = new FileServiceImp();
        userService = new UserServiceImp();
        eventService = new EventServiceImp();
        mapper = new FIleMapper();
//        logger = LoggerFactory.getLogger(FileRestControllerV1.class);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Integer idFile = Integer.parseInt(req.getPathInfo().replace("/",""));
        File file = fileService.getById(idFile);
        FileDto fileDto = mapper.map(file);
        writer.print(GsonUtils.toJSON(fileDto));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String fileName = fileService.downLoadFile(req);
        File file = File.builder()
                .name(fileName)
                .build();
        file = fileService.create(file);
        User user = userService.getById(Integer.valueOf(req.getHeader("id")));
        Event event = Event.builder()
                .file(file)
                .user(user)
                .build();
        eventService.create(event);
        FileDto dto = mapper.map(file);
        writer.print(toJSON(dto));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        File update = fileService.update(fromJson(getBody(req), File.class));
        FileDto fileDto = mapper.map(update);
        writer.print(toJSON(fileDto));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        File file = fromJson(getBody(req), File.class);
        boolean result = fileService.deleteById(file.getId());
        if(result) writer.print("File deleted is successful, result:"+result);
    }

}
