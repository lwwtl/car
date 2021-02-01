package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rlw.common.dto.CarDto;
import com.rlw.common.dto.MyOrderDto;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;
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


    /**
     * 左连接查询
     * 根据用户id查询出车辆名和对应订单
     * */
    @GetMapping("/myOrderList/{id}")
    public Result myOrderList(@RequestParam(defaultValue = "1") Integer currentPage,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "") String status,
                              @PathVariable(name = "id") Long id) {

            PageHelper.startPage(currentPage,pageSize);
            List<MyOrderDto> orders = orderService.findMyOrder(id,status);
            PageInfo<MyOrderDto> pageInfo = new PageInfo<>(orders);
            return Result.succ(pageInfo);

    }


    @PostMapping("/edit")
    public Result edit(@RequestBody Order order) {
        if (order.getOrderId() != null) {
            order.setOrderState("进行中");
            redisTemplate.delete("order:"+order.getOrderId());
            orderService.saveOrUpdate(order);
            return Result.succ(order);
        }else {
            Long id = Long.valueOf(getOrderIdByTime());
            order.setOrderId(id);
            order.setOrderCreate(LocalDateTime.now());
            order.setOrderState("待支付");
            orderService.save(order);
            /*订单支付剩余时间*/
            redisTemplate.opsForValue().set("order:"+order.getOrderId(),order.getOrderCreate());
            redisTemplate.expire("order:"+order.getOrderId(),900, TimeUnit.SECONDS);
            return Result.succ((order.getOrderId()).toString());
        }

    }


    @GetMapping("/pay/{id}")
    public Result pay(@PathVariable(name = "id") Long id) {
        Order order = orderService.getById(id);
        order.setOrderState("进行中");
        redisTemplate.delete("order:"+order.getOrderId());
        orderService.saveOrUpdate(order);
        return Result.succ("支付成功");
    }

    @GetMapping("/close/{id}")
    public Result close(@PathVariable(name = "id") Long id) {
        Order order = orderService.getById(id);
        order.setOrderState("已取消");
        redisTemplate.delete("order:"+order.getOrderId());
        orderService.saveOrUpdate(order);
        return Result.succ("取消成功");
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

    /**
     * 订单id生成策略
     * */
    public String getOrderIdByTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }
}
