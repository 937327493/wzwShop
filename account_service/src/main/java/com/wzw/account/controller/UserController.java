package com.wzw.account.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.account.entity.User;
import com.wzw.account.form.LoginForm;
import com.wzw.account.form.UserForm;
import com.wzw.account.mapper.UserMapper;
import com.wzw.account.service.UserService;
import com.wzw.account.util.JwtUtil;
import com.wzw.account.util.Md5Util;
import com.wzw.account.vo.UserVo;
import com.wzw.exceptions.Assert;
import com.wzw.exceptions.WzwRunException;
import com.wzw.result.ResponseEnum;
import com.wzw.templates.R;
import com.wzw.util.RUtil;
import com.wzw.util.RegexValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("register")
    public R register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new WzwRunException(ResponseEnum.USER_INFO_NULL.getMsg());
        }
        //判断手机号格式是否正确
        Assert.isTrue(RegexValidateUtil.checkMobile(userForm.getMobile()),ResponseEnum.MOBILE_ERROR);
        //判断手机号是否存在于数据库
        User one = userService.getOne(userForm.getMobile());
        if (one != null) {
            throw new WzwRunException(ResponseEnum.USER_MOBILE_EXIST.getMsg());
        }
        //判断验证码是否正确,根据电话作为key存在redis里边
        String o = (String)redisTemplate.opsForValue().get("wzwshop-sms-code-" + userForm.getMobile());
        if (o != null) {
            Assert.equals(o,userForm.getCode(),ResponseEnum.USER_CODE_ERROR);
        }else throw new WzwRunException(ResponseEnum.USER_CODE_ERROR.getMsg());

        //下面手机号没被注册过、验证码也正确，可以注册
        User user = new User();
        user.setMobile(userForm.getMobile());
        //密码加密加盐
        user.setPassword(Md5Util.getSaltMD5(userForm.getPassword()));
        boolean save = userService.save(user);
        if (save) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }


    //Get不能接收json
    @GetMapping("login")
    public R login(@Valid  LoginForm loginForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new WzwRunException(ResponseEnum.USER_INFO_NULL.getMsg());
        }
        //数据库是否有这个电话
        User one = userService.getOne(loginForm.getMobile());
        if (one == null) {
            throw new WzwRunException(ResponseEnum.USERNAME_NULL.getMsg());
        }
        //密码验证是否可以通过
        if (!Md5Util.getSaltverifyMD5(loginForm.getPassword(),one.getPassword())) {
            throw new WzwRunException(ResponseEnum.PASSWORD_ERROR.getMsg());
        }
        //校验都通后将Token返回前端
        UserVo userVo = new UserVo();
        userVo.setMobile(loginForm.getMobile());
        userVo.setPassword(loginForm.getPassword());
        userVo.setUserId(one.getUserId());
        userVo.setToken(JwtUtil.createToken(one.getUserId(),one.getMobile()));
        return RUtil.success(userVo);
    }


    @GetMapping("token/{token}")
    public R token(@PathVariable("token") String token){
        boolean b = JwtUtil.checkToken(token);
        if (b) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }
}

