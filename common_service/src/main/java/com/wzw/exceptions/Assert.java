package com.wzw.exceptions;


import com.wzw.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Assert {

    public static void notNull(Object obj, ResponseEnum responseEnum){
        if(obj == null){
            log.info("数据为null");
            throw new WzwRunException(responseEnum.getMsg());
        }
    }

    public static void equals(Object obj1, Object obj2, ResponseEnum responseEnum) {
        if (!obj1.equals(obj2)) {
            log.info("{}和{}不相等",obj1,obj2);
            throw new WzwRunException(responseEnum.getMsg());
        }
    }

    public static void isTrue(boolean expression, ResponseEnum responseEnum) {
        if (!expression) {
            log.info("验证失败");
            throw new WzwRunException(responseEnum.getMsg());
        }
    }

}
