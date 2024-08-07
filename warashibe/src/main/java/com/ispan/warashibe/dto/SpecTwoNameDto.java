package com.ispan.warashibe.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecTwoNameDto {
    private Integer specTwoNameID;
    private String specTwoName;
    private List<SpecTwoDto> specTwos;

    // Getters and setters
}