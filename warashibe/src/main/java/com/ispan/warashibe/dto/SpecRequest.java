package com.ispan.warashibe.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecRequest {
    private String specOneName;
    private List<SpecOneDto> specOnes;

    // Getters and setters
}