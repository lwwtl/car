package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rlw.common.dto.*;
import com.rlw.common.lang.Result;
import com.rlw.entity.*;
import com.rlw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    RepairService repairService;

    @Autowired
    ViolationsService violationsService;

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
        String state = orderDto.getState();
        if (StringUtils.isEmpty(carNo) && StringUtils.isEmpty(userAccount) && StringUtils.isEmpty(id) && StringUtils.isEmpty(state)) {
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
            if(!StringUtils.isEmpty(state)){
                queryWrapper.eq("order_state",state);
            }
            IPage pageData = orderService.page(page, queryWrapper.orderByDesc("order_create"));
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
            return Result.succ(order);
        }

    }


    @GetMapping("/pay/{id}")
    public Result pay(@PathVariable(name = "id") Long id) {
        Order order = orderService.getById(id);
        order.setOrderState("待取车");
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

    @GetMapping("/storeOut/{id}")
    public Result storeOut(@PathVariable(name = "id") Long id) {
        Order order = orderService.getById(id);
        if(order!=null){
            order.setOrderState("进行中");
            Car car = carService.getById(order.getCarId());
            car.setState("已出库");

            Repair repair = new Repair();
            repair.setOrderId(order.getOrderId());
            repair.setCarId(order.getCarId());

            Violations violations = new Violations();
            violations.setOrderId(order.getOrderId());
            violations.setCarId(order.getCarId());
            violations.setUserId(order.getUserId());

            try {
                repairService.save(repair);
                violationsService.save(violations);
                orderService.saveOrUpdate(order);
                carService.saveOrUpdate(car);
            }catch (Exception e){
                return Result.fail("请勿重复出库!");
            }
            return Result.succ("出库成功");
        }else {
            return Result.fail("该订单不存在!");
        }
    }

    @GetMapping("/storeIn/{id}")
    public Result storeIn(@PathVariable(name = "id") Long id) {
        QueryWrapper<Repair> q1 = new QueryWrapper<>();
        q1.eq("order_id",id);
        Repair repair = repairService.getOne(q1);
        String repairRecorder = repair.getRepairRecorder();
        QueryWrapper<Violations> q2 = new QueryWrapper<>();
        q2.eq("order_id",id);
        Violations violations = violationsService.getOne(q2);
        String violationsRecorder = violations.getViolationsRecorder();
        if(StringUtils.isEmpty(repairRecorder) || StringUtils.isEmpty(violationsRecorder)){
            return Result.fail("请先完成入库登记再执行入库操作!");
        }else {
            BigDecimal repairCost = new BigDecimal(String.valueOf(repair.getRepairCost()));
            BigDecimal violationsCost = new BigDecimal(String.valueOf(violations.getViolationsCost()));
            BigDecimal totalCost = repairCost.add(violationsCost);
            System.out.println("扣除押金:"+totalCost);
            Order order = orderService.getById(id);
            order.setOrderExtra(totalCost);
            order.setOrderState("已完成");
            Car car = carService.getById(order.getCarId());
            if(repair.getRepairIf().equals(1L)){
                car.setState("维修中");
            }else {
                car.setState("已入库");
            }
            orderService.saveOrUpdate(order);
            carService.saveOrUpdate(car);
            return Result.succ("入库成功");
        }
    }

    @GetMapping("drawFold")
    public Result drawFold() {
        List<OrderStatisticsDto> drawFold = orderService.drawFold();
        return Result.succ(drawFold);
    }
    @GetMapping("drawLine")
    public Result drawLine() {
        List<CatHotStaticsDto> drawLine = orderService.drawLine();
        return Result.succ(drawLine);
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
