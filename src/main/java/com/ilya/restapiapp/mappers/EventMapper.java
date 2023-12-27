package com.ilya.restapiapp.mappers;

import com.ilya.restapiapp.dto.EventDto;
import com.ilya.restapiapp.model.Event;


import java.util.List;
import java.util.stream.Collectors;

public class EventMapper implements GenericMapper<Event, EventDto>{

    @Override
    public EventDto map(Event event) {
        if(event == null) return null;
        return EventDto.builder()
                .id(event.getId())
                .fileId(event.getFile().getId())
                .userId(event.getUser().getId())
                .build();
    }

    @Override
    public List<EventDto> map(List<Event> events) {
        if(events==null || events.size()==0) return null;
        return events.stream().map(this::map).collect(Collectors.toList());
    }

}
