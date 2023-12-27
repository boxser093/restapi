package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.dto.EventDto;
import com.ilya.restapiapp.dto.UserDto;
import com.ilya.restapiapp.mappers.EventMapper;
import com.ilya.restapiapp.mappers.UserMapper;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.service.impl.EventServiceImp;

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
@WebServlet(name = "EventRestControllerV1", urlPatterns = "/api/v1/events**")
public class EventRestControllerV1 extends HttpServlet {

//    private Logger logger = LoggerFactory.getLogger(EventRestControllerV1.class);

    private EventServiceImp eventService;
    private EventMapper eventMapper;
    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = new EventServiceImp();
        eventMapper = new EventMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String getBody = req.getPathInfo();
        if(getBody.contains("all")){
            List<Event> event = eventService.getAll();
            List<EventDto> eventDtos = eventMapper.map(event);
            writer.print(toJSON(eventDtos));
        } else {
            Integer idUser = Integer.valueOf(req.getPathInfo().replace("/",""));
            Event event = eventService.getById(idUser);
            EventDto dto = eventMapper.map(event);
            writer.print(toJSON(dto));
        }
    }

}
