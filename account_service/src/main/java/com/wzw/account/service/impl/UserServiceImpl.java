package com.wzw.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.account.entity.User;
import com.wzw.account.mapper.UserMapper;
import com.wzw.account.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.util.RUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
@Resource
UserMapper userMapper;
    @Override
    public User getOne(String phone) {
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("mobile",phone);
        User user = userMapper.selectOne(objectQueryWrapper);
        return user;
    }
}
