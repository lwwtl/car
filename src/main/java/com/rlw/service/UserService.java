package com.rlw.service;

import com.rlw.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-08
 */
public interface UserService extends IService<User> {
    String findByAccount(String account);
}
