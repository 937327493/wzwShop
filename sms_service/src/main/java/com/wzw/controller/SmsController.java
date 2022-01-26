package com.wzw.controller;

import com.wzw.exceptions.Assert;
import com.wzw.result.ResponseEnum;
import com.wzw.service.SmsService;
import com.wzw.templates.R;
import com.wzw.util.RUtil;
import com.wzw.util.RandomUtil;
import com.wzw.util.RegexValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    SmsService smsService;

    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("send/{mobel}")
    public R send(@PathVariable("mobel") String mobel) {
        //手机号是否为空
        Assert.notNull(mobel, ResponseEnum.PARAMETER_NULL);
        //手机号格式是否错误
        Assert.isTrue(RegexValidateUtil.checkMobile(mobel),ResponseEnum.MOBILE_ERROR);
        //生成随机的验证码
        String sixBitRandom = RandomUtil.getSixBitRandom();
        System.out.println(sixBitRandom);
        //将验证码发送出去
        boolean send = smsService.send(mobel, sixBitRandom);
        if (send) {
            //发送成功我们应该存到redis
            redisTemplate.opsForValue().set("wzwshop-sms-code-"+mobel, sixBitRandom);
            return RUtil.success("短信发送成功！");
        }
        //测试时候不需要短信
        redisTemplate.opsForValue().set("wzwshop-sms-code-"+mobel, sixBitRandom);
        return RUtil.success("短信发送成功！");
    }
}
