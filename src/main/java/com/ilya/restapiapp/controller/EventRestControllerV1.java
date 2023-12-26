package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.service.impl.EventServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "EventRestControllerV1", urlPatterns = "/api/v1/events**")
public class EventRestControllerV1 extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(EventRestControllerV1.class);

    private EventServiceImp eventService = new EventServiceImp();
    // Всех
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Event event = eventService.fromJson(eventService.getBody(req));
        event = eventService.getById(event.getId());
        writer.print(eventService.toJSON(event));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String body = eventService.getBody(req);
        if(body.contains("all")) {
            List<Event> events = eventService.getAll();
            writer.print(eventService.toJSONAll(events));
        }
    }
}
