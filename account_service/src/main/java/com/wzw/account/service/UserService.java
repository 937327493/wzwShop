package com.wzw.account.service;

import com.wzw.account.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-19
 */
public interface UserService extends IService<User> {
    public User getOne(String phone);
}
