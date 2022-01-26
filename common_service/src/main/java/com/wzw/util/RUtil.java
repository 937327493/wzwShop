package com.wzw.util;

import com.wzw.templates.R;

public class RUtil {
    public static R success(Object data){
        R r = new R();
        r.setData(data);
        r.setCode(0);
        r.setMsg("成功");
        return r;
    }
    public static R fail(String s){
        R r = new R();
        r.setData(null);
        r.setCode(1);
        r.setMsg(s);
        return r;
    }
}
