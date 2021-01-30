package com.rlw.service;

import com.rlw.common.dto.MyOrderDto;
import com.rlw.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-12
 */
public interface OrderService extends IService<Order> {
    String findByNo(String carNo);
    List<MyOrderDto> findMyOrder(Long id);
}
