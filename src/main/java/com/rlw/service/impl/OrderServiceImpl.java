package com.rlw.service.impl;

import com.rlw.entity.Order;
import com.rlw.mapper.OrderMapper;
import com.rlw.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
