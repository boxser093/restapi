package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.dto.EventDto;
import com.ilya.restapiapp.mappers.EventMapper;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.service.impl.EventServiceImp;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
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
    @Operation(
            summary = "Event Get By Id",
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
                                    allOf = {String.class}
                            ),
                            style = ParameterStyle.SIMPLE
                    ),
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "all",
                            required = true,
                            description = "If you need all Events, chose 'all' in path query",
                            schema = @Schema(
                                    defaultValue = "all",
                                    minimum = "1",
                                    allOf = {String.class}
                            )
                    )
            }
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
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
