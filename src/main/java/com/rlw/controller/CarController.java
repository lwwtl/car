package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rlw.common.dto.CarBrandDto;
import com.rlw.common.dto.CarDto;
import com.rlw.common.dto.CarTypeDto;
import com.rlw.common.dto.MyViolationsDto;
import com.rlw.common.lang.Result;
import com.rlw.entity.Car;

import com.rlw.entity.Store;
import com.rlw.entity.User;
import com.rlw.mapper.CarMapper;
import com.rlw.mapper.StoreMapper;
import com.rlw.provider.UCloudProvider;
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


@Autowired
private UCloudProvider uCloudProvider;
    @Autowired
    StoreService storeService;


    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "5") Integer pageSize, @RequestBody CarDto carDto) {
        Page page = new Page(currentPage, pageSize);
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        String no = carDto.getNo();
        String name = carDto.getName();
        String region = carDto.getRegion();
        String source = carDto.getSource();
        String start = carDto.getStart();
        String end = carDto.getEnd();
        String status = carDto.getStatus();
        /*前台查询门店可组车辆*/
        if(!StringUtils.isEmpty(source)){
            PageHelper.startPage(currentPage,pageSize);
            List<Car> cars = carService.findRentCar(source,start,end,status);
            PageInfo<Car> pageInfo = new PageInfo<>(cars);
            return Result.succ(pageInfo);
        }
        //String state = car.getState();
        /*后台查询*/
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(no) && StringUtils.isEmpty(region)) {
            if(!StringUtils.isEmpty(status)){
                if(status.equals("1")){
                    IPage pageData = carService.page(page, queryWrapper.orderByDesc("car_output"));
                    return Result.succ(pageData);
                }else if(status.equals("2")){
                    IPage pageData = carService.page(page, queryWrapper.orderByAsc("car_output"));
                    return Result.succ(pageData);
                }else if(status.equals("3")){
                    IPage pageData = carService.page(page, queryWrapper.orderByDesc("car_rent"));
                    return Result.succ(pageData);
                }else if(status.equals("4")){
                    IPage pageData = carService.page(page, queryWrapper.orderByAsc("car_rent"));
                    return Result.succ(pageData);
                }
            }
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

    /**
     * 根据车型来挑选车辆
     * */
    @PostMapping("/chooseType")
    public Result chooseType(@RequestParam(defaultValue = "1") Integer currentPage,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             @RequestBody CarTypeDto carTypeDto) {
        String city = carTypeDto.getCity();
        /*前台查询门店可组车辆*/
        if(!StringUtils.isEmpty(city)){
            String type = carTypeDto.getType();
            String brand = carTypeDto.getBrand();
            String start = carTypeDto.getStart();
            String end = carTypeDto.getEnd();
            PageHelper.startPage(currentPage,pageSize);
            List<Car> cars = carService.findCarByType(city,type,brand,start,end);
            PageInfo<Car> pageInfo = new PageInfo<>(cars);
            return Result.succ(pageInfo);
        }else {
            return Result.fail("城市未选择");
        }
    }


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

    @GetMapping("/findStore/{id}")
    public Result findStore(@PathVariable(name = "id") Long id) {
        Car car = carService.getById(id);
        String storeName = car.getStore();
        return Result.succ(storeName);
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id") Long id) {
        Car car = carService.getById(id);
        Assert.notNull(car, "该车辆已被删除");
        carService.removeById(id);
        return Result.succ(null);
    }


    @GetMapping("carRate")
    public Result carRate(){
        String carCount = carService.carCount();
        String carRent = carService.carRent();
        StringBuilder rt = new StringBuilder();
        rt.append(carCount).append(",").append(carRent);
        return Result.succ(rt);
    }

    @GetMapping("brandList")
    public Result brandList(){
        List<CarBrandDto> brandList = carService.brandList();
        return Result.succ(brandList);
    }

    /**
     * 七牛云图片上传
     * */
    /*@PostMapping("/upload/img")
    public Result upload(MultipartFile file)throws IOException {
        //      图片上传到服务器
        String imageUrl = new UploadUtil().upload(UUID.randomUUID().toString(),file.getBytes());
        return Result.succ(imageUrl);*/

    /**
     * UCloud图片上传
     * */
    @PostMapping("/upload/img")
    public Result upload(MultipartFile file)throws IOException {
        //      图片上传到服务器
        String imageUrl = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
        return Result.succ(imageUrl);
}

    /**
     * 本地图片上传
     * */
//    @PostMapping("/upload/img")
//    public Result upload(MultipartFile file){
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
