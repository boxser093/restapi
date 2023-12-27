package com.ilya.restapiapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Integer id;

    private Integer userId;

    private Integer fileId;
}
