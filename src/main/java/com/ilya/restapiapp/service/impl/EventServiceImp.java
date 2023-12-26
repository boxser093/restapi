package com.ilya.restapiapp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.repository.EventRepository;
import com.ilya.restapiapp.repository.impl.HibEventRepImpl;
import com.ilya.restapiapp.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class EventServiceImp implements EventService {
    private EventRepository eventRepository;
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private final Logger logger = LoggerFactory.getLogger(EventServiceImp.class);

    public EventServiceImp() {
        this.eventRepository = new HibEventRepImpl();
    }

    @Override
    public Event getById(Integer id) {
        Event result = eventRepository.getById(id);
        logger.info("## Event Service ## get By id()");
        return result;
    }

    @Override
    public Event create(Event event) {
        Event beforeSave = eventRepository.save(event);
        logger.info("## Event Service ## save()");
        return beforeSave;
    }

    @Override
    public boolean deleteById(Integer integer) {
        return eventRepository.deleteById(integer);
    }

    @Override
    public Event update(Event event) {
        return eventRepository.update(event);
    }
    @Override
    public List<Event> getAll() {
        List<Event> result = eventRepository.getAll();
        return result;
    }
    public String toJSON(Event event) {
        return gson.toJson(event);
    }

    public String toJSONAll(List<Event> events) {
        return gson.toJson(events);
    }

    public Event fromJson(String bodyRequest) {
        return gson.fromJson(bodyRequest, Event.class);
    }

    public String getBody(HttpServletRequest req) throws IOException {
        BufferedReader br = req.getReader();
        String str, result = "";
        while ((str = br.readLine()) != null) {
            result += str;
        }
        return result;
    }

}
