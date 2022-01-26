package com.wzw.templates;

import lombok.Data;

@Data
public class R<T> {
    private String msg;
    private int code;
    private T data;
}
