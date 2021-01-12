package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.dto.CarDto;
import com.rlw.common.lang.Result;
import com.rlw.entity.Car;

import com.rlw.entity.Store;
import com.rlw.entity.User;
import com.rlw.mapper.CarMapper;
import com.rlw.mapper.StoreMapper;
import com.rlw.service.CarService;
import com.rlw.service.StoreService;
import com.rlw.util.UploadUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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

    @Autowired(required = false)
    StoreMapper storeMapper;

    @Autowired
    StoreService storeService;


    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "5") Integer pageSize, @RequestBody CarDto carDto) {
        Page page = new Page(currentPage, pageSize);
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        String no = carDto.getNo();
        String name = carDto.getName();
        String region = carDto.getRegion();
        String store = carDto.getSource();
        String start = carDto.getStart();
        String end = carDto.getEnd();
        if(!StringUtils.isEmpty(store)){
            List<Car> cars = carService.findRentCar(store,start,end);
            System.out.println(cars);
        }
        //String state = car.getState();
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(no) && StringUtils.isEmpty(region)) {
            IPage pageData = carService.page(page, new QueryWrapper<Car>().orderByDesc("car_id"));
            return Result.succ(pageData);
        } else {
            if (!StringUtils.isEmpty(name)) {
                queryWrapper.like("car_name", carDto.getName());
            }
            if (!StringUtils.isEmpty(no)) {
                queryWrapper.like("car_no", carDto.getNo());
            }
            if (!StringUtils.isEmpty(region)) {
                queryWrapper.eq("car_region", carDto.getRegion());
            }
            IPage pageData = carService.page(page, queryWrapper.orderByDesc("car_id"));
            return Result.succ(pageData);
        }
    }

    /*

        select *
        from t_car
        where
        car_state != '维修中'
        and car_id not in
        (
        select car_id
        from t_order
        where
        '2021-01-11 17:58:53' BETWEEN  order_start and order_end
        or '2021-01-13 17:54:53' BETWEEN  order_start  and order_end
        )


     */



    @PostMapping("/edit")
    public Result edit(@RequestBody Car car) {
        Car repeatNo = carService.getOne(new QueryWrapper<Car>().eq("car_no", car.getNo()));
        if (repeatNo != null && car.getId() == null) {
            return Result.fail("该车牌号已存在");
        }
        if (repeatNo != null && !repeatNo.getId().equals(car.getId())) {
            return Result.fail("该车牌号已存在");
        }
        if (car.getId() != null) {
            carService.saveOrUpdate(car);
        } else {
            carService.save(car);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id") Long id) {
        Car car = carService.getById(id);
        Assert.notNull(car, "该车辆已被删除");
        return Result.succ(car);
    }

    @PostMapping("/findStore")
    public Result find(@RequestParam(name = "region") String region) {
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.eq("store_address",region);
        List <Store> storeList = storeMapper.selectList(wrapper);
        return Result.succ(storeList);
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id") Long id) {
        Car car = carService.getById(id);
        Assert.notNull(car, "该车辆已被删除");
        carService.removeById(id);
        return Result.succ(null);
    }

    @PostMapping("/upload/img")
    public Result upload(MultipartFile file)throws IOException {
        //      图片上传到服务器
        String imageUrl = new UploadUtil().upload(UUID.randomUUID().toString(),file.getBytes());
        return Result.succ(imageUrl);
}

//    @PostMapping("/upload/img")
//    public Result upload(MultipartFile file){
//        /*本地图片上传*/
//        String fileName = file.getOriginalFilename();  // 文件名
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
//        String filePath = "E://毕业设计//upload//"; // 上传后的路径
//        fileName = UUID.randomUUID() + suffixName; // 新文件名
//        File dest = new File(filePath + fileName);
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(dest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Result.succ(fileName);




}
