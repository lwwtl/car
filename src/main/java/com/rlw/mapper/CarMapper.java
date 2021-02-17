package com.rlw.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.dto.CarBrandDto;
import com.rlw.entity.Car;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 饶立玮
 * @since 2020-12-24
 */
public interface CarMapper extends BaseMapper<Car> {
    List<Car> findRentCar(@Param("source")String source, @Param("start")String start, @Param("end")String end,@Param("status") String status);
    String carCount();
    String carRent();
    List<CarBrandDto> brandList();
    List<Car> findCarByType(@Param("city")String city,@Param("type")String type,@Param("brand")String brand,@Param("start")String start,@Param("end")String end);
}
