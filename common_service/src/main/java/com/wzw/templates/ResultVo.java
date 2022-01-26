package com.wzw.templates;

import lombok.Data;

import java.util.List;

@Data
public class ResultVo<T> {
    private int total;
    private int size;
    private List<T> content;
}
