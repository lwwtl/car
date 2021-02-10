package com.rlw.service.impl;

import com.rlw.entity.User;
import com.rlw.mapper.UserMapper;
import com.rlw.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public String findByAccount(String account) {
        return userMapper.findByAccount(account);
    }

    @Override
    public String userNum() {
        return userMapper.userNum();
    }
}
