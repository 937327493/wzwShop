package com.wzw.service.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.wxapi.WxApiCall.WxApiCall;
import com.wxapi.model.RequestModel;
import com.wzw.exceptions.WzwRunException;
import com.wzw.result.ResponseEnum;
import com.wzw.service.SmsService;
import com.wzw.util.SmsUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public boolean send(String mobile, String code) {
        System.out.println("SmsUtil.Appkey"+SmsUtil.Appkey);
        System.out.println("SmsUtil.Sign"+SmsUtil.Sign);
        System.out.println("SmsUtil.Url"+SmsUtil.Url);
        if (SmsUtil.Appkey == null || SmsUtil.Url == null || SmsUtil.Sign == null)
            return false;
//        return false;
        RequestModel model = new RequestModel();
        model.setGwUrl(SmsUtil.Url);
        model.setAppkey(SmsUtil.Appkey);
        Map queryMap = new HashMap();
        queryMap.put("sign",SmsUtil.Sign); //访问参数
        queryMap.put("mobile",mobile); //访问参数
        queryMap.put("content","【我是王梓崴】您的验证码是："+code); //访问参数
        model.setQueryParams(queryMap);
        System.out.println(model.getBodyStr()+"1");
        try {
            WxApiCall call = new WxApiCall();
            System.out.println(model.getBodyStr()+"2");
            call.setModel(model);
            System.out.println(model.getBodyStr()+"3");
//            call.request();
//            String request = call.request();
//            System.out.println(model.getBodyStr()+"4");
//            Gson gson = new Gson();
//            Map<String,String> map = gson.fromJson(request,
//                    new TypeToken<Map<String,String>>(){}.getType());
            System.out.println(model.getBodyStr()+"5");

//            System.out.println(map);
//            if(map.get("code").equals("10010"))return true;
        } catch (JsonSyntaxException e) {
            throw new WzwRunException(ResponseEnum.SMS_SEND_ERROR.getMsg());
        }
        return false;
    }
}
