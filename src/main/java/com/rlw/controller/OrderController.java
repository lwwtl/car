package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.dto.OrderDto;
import com.rlw.common.lang.Result;
import com.rlw.entity.Car;
import com.rlw.entity.Order;
import com.rlw.entity.Store;
import com.rlw.service.CarService;
import com.rlw.service.OrderService;
import com.rlw.service.StoreService;
import com.rlw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-12
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    CarService carService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestBody OrderDto orderDto) {
        Page page = new Page(currentPage, pageSize);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        String carNo = orderDto.getCno();
        String userAccount = orderDto.getAccount();
        Long id = orderDto.getId();
        if (StringUtils.isEmpty(carNo) && StringUtils.isEmpty(userAccount) && StringUtils.isEmpty(id)) {
            IPage pageData = orderService.page(page, new QueryWrapper<Order>().orderByDesc("order_id"));
            return Result.succ(pageData);
        } else {
            if (!StringUtils.isEmpty(carNo)) {
                String carId = orderService.findByNo(orderDto.getCno());
                queryWrapper.eq("car_id", carId);
            }
            if (!StringUtils.isEmpty(userAccount)) {
                String userId = userService.findByAccount(orderDto.getAccount());
                queryWrapper.eq("user_id", userId);
            }
            if (!StringUtils.isEmpty(id)) {
                queryWrapper.eq("order_id", id);
            }
            IPage pageData = orderService.page(page, queryWrapper.orderByDesc("order_id"));
            return Result.succ(pageData);
        }
    }


    @PostMapping("/edit")
    public Result edit(@RequestBody Order order) {
        if (order.getOrderId() != null) {
            orderService.saveOrUpdate(order);
        }else {
            order.setOrderCreate(LocalDateTime.now());
            order.setOrderState("待支付");
            orderService.save(order);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id") Long id) {
        Order order = orderService.getById(id);
        Assert.notNull(order, "该订单已被删除");
        return Result.succ(order);
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id") Long id) {
        Order order = orderService.getById(id);
        Assert.notNull(order, "该订单已被删除");
        orderService.removeById(id);
        return Result.succ(null);
    }
}
