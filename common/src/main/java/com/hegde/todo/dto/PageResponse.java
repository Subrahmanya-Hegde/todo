package com.hegde.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private boolean hasMore;
    private long totalCount;
    private int pageNo;
    private int pageSize;
    private List<T> content;
}
