package com.rlw.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.entity.Car;
import com.rlw.mapper.CarMapper;
import com.rlw.service.CarService;
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
 * @since 2020-12-24
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    @Autowired(required = false)
    private CarMapper carMapper;

    @Override
    public List<Car> findRentCar( String source, String start, String end,String status) {
        return carMapper.findRentCar(source,start,end,status);
    }
}
