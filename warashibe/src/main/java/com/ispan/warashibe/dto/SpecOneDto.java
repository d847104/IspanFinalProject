package com.ispan.warashibe.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecOneDto {
    private Integer specOneID;
    private String specOne;
    private List<SpecTwoNameDto> specTwoNames;

    // Getters and setters
}