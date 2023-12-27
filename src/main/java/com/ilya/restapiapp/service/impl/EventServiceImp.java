package com.ilya.restapiapp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.repository.EventRepository;
import com.ilya.restapiapp.repository.impl.HibEventRepImpl;
import com.ilya.restapiapp.service.EventService;
import java.util.List;

public class EventServiceImp implements EventService {
    private EventRepository eventRepository;

//    private final Logger logger = LoggerFactory.getLogger(EventServiceImp.class);

    public EventServiceImp() {
        this.eventRepository = new HibEventRepImpl();
    }

    @Override
    public Event getById(Integer id) {
        Event result = eventRepository.getById(id);
//        logger.info("## Event Service ## get By id()");
        return result;
    }

    @Override
    public Event create(Event event) {
        Event beforeSave = eventRepository.save(event);
//        logger.info("## Event Service ## save()");
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


}
