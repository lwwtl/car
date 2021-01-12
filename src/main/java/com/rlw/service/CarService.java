package com.rlw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.entity.Car;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 饶立玮
 * @since 2020-12-24
 */
public interface CarService extends IService<Car> {
    List<Car> findRentCar( String source, String start, String end);
}
