package com.ispan.warashibe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageRequestDTO {
    private int page;
    private int size;
    private String sortBy; // 排序屬性
    private String sortDirection; // 排序方向 (ASC 或 DESC)
}
