package com.rlw.mapper;

import com.rlw.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-12
 */
public interface OrderMapper extends BaseMapper<Order> {
    String findByNo(String carNo);
}
