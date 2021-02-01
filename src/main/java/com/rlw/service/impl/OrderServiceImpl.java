package com.rlw.service.impl;

import com.rlw.common.dto.MyOrderDto;
import com.rlw.entity.Order;
import com.rlw.mapper.CarMapper;
import com.rlw.mapper.OrderMapper;
import com.rlw.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Override
    public String findByNo(String carNo) {
        return orderMapper.findByNo(carNo);
    }

    @Override
    public List<MyOrderDto> findMyOrder(Long id,String status) {
        return orderMapper.findMyOrder(id,status);
    }
}
