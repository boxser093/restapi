package com.ilya.restapiapp.controller;

import com.ilya.restapiapp.dto.FileDto;
import com.ilya.restapiapp.mappers.FIleMapper;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.model.File;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.service.impl.EventServiceImp;
import com.ilya.restapiapp.service.impl.FileServiceImp;
import com.ilya.restapiapp.service.impl.UserServiceImp;
import com.ilya.restapiapp.util.GsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static com.ilya.restapiapp.util.GsonUtils.*;
import static com.ilya.restapiapp.util.RequestUtils.*;

@Slf4j
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

    @Operation(
            summary = "Get File By Id",
            description = "Get File by ID",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            required = true,
                            description = "ID of File",
                            schema = @Schema(
                                    defaultValue = "1",
                                    minimum = "1",
                                    allOf = {String.class}
                            ),
                            style = ParameterStyle.SIMPLE
                    )
            }
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Integer idFile = Integer.parseInt(req.getPathInfo().replace("/",""));
        File file = fileService.getById(idFile);
        FileDto fileDto = mapper.map(file);
        writer.print(GsonUtils.toJSON(fileDto));
    }
    @Operation(
            summary = "Upload New File",
            description = "Upload a New File",
            parameters = {
                    @Parameter(
                            in = ParameterIn.HEADER,
                            name = "id",
                            required = true,
                            description = "ID User",
                            schema = @Schema(
                                    defaultValue = "1",
                                    minimum = "1",
                                    allOf = {String.class}
                            )
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "file",
                            required = true,
                            description = "File to Upload",
                            schema = @Schema(
                                    allOf = {File.class}
                            )
                    )
            }
    )
    @RequestBody(
            description = "We need into a ID user in headers and file int body-params with form-data",
            required = true,
            content = @Content(mediaType = "form-data")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
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
    @Operation(
            summary = "Update File",
            description = "Update name for you upload files"
    )
    @RequestBody(
            description = "Input new File name and id",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        File update = fileService.update(fromJson(getBody(req), File.class));
        FileDto fileDto = mapper.map(update);
        writer.print(toJSON(fileDto));

    }
    @Operation(
            summary = "Deleted File By Id",
            description = "Deleted File by ID",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            required = true,
                            description = "ID of File",
                            schema = @Schema(
                                    defaultValue = "1",
                                    minimum = "1",
                                    allOf = {String.class}
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        File file = fromJson(getBody(req), File.class);
        boolean result = fileService.deleteById(file.getId());
        if(result) writer.print("File deleted is successful, result:"+result);
    }

}
