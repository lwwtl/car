package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.lang.Result;
import com.rlw.entity.Car;
import com.rlw.entity.Employee;
import com.rlw.service.CarService;
import com.rlw.util.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 饶立玮
 * @since 2020-12-24
 */
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "5") Integer pageSize,@RequestBody Car car){
        Page page = new Page(currentPage,pageSize);
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        String no = car.getNo();
        String name = car.getName();
        //String state = car.getState();
        if(StringUtils.isEmpty(name)&&StringUtils.isEmpty(no)){
            IPage pageData = carService.page(page, new QueryWrapper<Car>().orderByDesc("car_id"));
            return Result.succ(pageData);
        }else {
            if (!StringUtils.isEmpty(name)){
                queryWrapper.like("car_name",car.getName());
            }
            if (!StringUtils.isEmpty(no)){
                queryWrapper.like("car_no",car.getNo());
            }
            IPage pageData = carService.page(page,queryWrapper.orderByDesc("car_id"));
            return Result.succ(pageData);
        }
    }


    @GetMapping("/{id}")
    public Result index(@PathVariable("id") Long id){
        Car car = carService.getById(id);
        System.out.println(car);
        return Result.succ(200,"操作成功",car);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Car car) {
        Car repeatNo = carService.getOne(new QueryWrapper<Car>().eq("car_no",car.getNo()));
        if(repeatNo != null && car.getId()==null){
            return Result.fail("该车牌号已存在");
        }
        if(repeatNo != null && !repeatNo.getId().equals(car.getId())){
            return Result.fail("该车牌号已存在");
        }
        if(car.getId() != null){
            carService.saveOrUpdate(car);
        }else {
            carService.save(car);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id")Long id){
        Car car = carService.getById(id);
        Assert.notNull(car,"该车辆已被删除");
        return Result.succ(car);
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id")Long id){
        Car car = carService.getById(id);
        Assert.notNull(car,"该车辆已被删除");
        carService.removeById(id);
        return Result.succ(null);
    }

}
