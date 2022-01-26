package com.wzw.account.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.account.entity.Admin;
import com.wzw.account.service.AdminService;
import com.wzw.account.util.JwtUtil;
import com.wzw.account.vo.AdminVo;
import com.wzw.exceptions.WzwRunException;
import com.wzw.result.ResponseEnum;
import com.wzw.templates.R;
import com.wzw.util.RUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-19
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping("login/{userName}/{passWord}")
    public R login(@PathVariable("userName")String userName, @PathVariable("passWord")String passWord){
        QueryWrapper<Admin> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("username",userName);
        objectQueryWrapper.eq("password",passWord);
        Admin admin = adminService.getOne(objectQueryWrapper);
        if (admin == null) {
            return RUtil.fail("密码错误");
//            throw new WzwRunException(ResponseEnum.PASSWORD_ERROR.getMsg());
        }
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin,adminVo);
        adminVo.setToken(JwtUtil.createToken(admin.getAdminId(),admin.getName()));
        return RUtil.success(adminVo);
    }
    @GetMapping("checkToken/{token}")
    public R checkToken(@PathVariable("token")String token){
        boolean b = JwtUtil.checkToken(token);
        if (b) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }
}

