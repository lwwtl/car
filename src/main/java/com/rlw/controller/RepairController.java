package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.lang.Result;
import com.rlw.entity.Repair;
import com.rlw.service.RepairService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 饶立玮
 * @since 2021-02-04
 */
@RestController
@RequestMapping("/repair")
public class RepairController {


    @Autowired
    RepairService repairService;

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "5") Integer pageSize, @RequestBody Repair repair) {
        Page page = new Page(currentPage, pageSize);
        QueryWrapper<Repair> queryWrapper = new QueryWrapper<>();
        Long orderId = repair.getOrderId();
        /*待处理列表展示*/
       /* String repairRecorder = repair.getRepairRecorder();*/
        if (StringUtils.isEmpty(orderId)) {

            IPage pageData = repairService.page(page, new QueryWrapper<Repair>().orderByDesc("repair_create"));
            return Result.succ(pageData);
        } else {
            queryWrapper.eq("order_id", repair.getOrderId());
            IPage pageData = repairService.page(page, queryWrapper.orderByDesc("repair_create"));
            return Result.succ(pageData);
        }
    }



    @PostMapping("/edit")
    public Result edit(@RequestBody Repair repair) {
         if (repair.getRepairId() != null) {
             repair.setRepairCreate(LocalDateTime.now());
            repairService.saveOrUpdate(repair);
        }else {
             repairService.save(repair);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id") Long id) {
        QueryWrapper<Repair> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",id);
        Repair repair = repairService.getOne(queryWrapper);
        return Result.succ(repair);
    }

}
