package com.rlw.mapper;

import com.rlw.common.dto.MyOrderDto;
import com.rlw.entity.Car;
import com.rlw.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<MyOrderDto> findMyOrder(@Param("id")Long id,@Param("status")String status);

}
