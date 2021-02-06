package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rlw.common.dto.RepairDto;
import com.rlw.common.dto.ViolationsDto;
import com.rlw.common.lang.Result;
import com.rlw.entity.Repair;
import com.rlw.service.RepairService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


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
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestBody RepairDto repairDto) {
        Long id = repairDto.getOrderId();
        String no = repairDto.getCarNo();
        String recorder = repairDto.getRepairRecorder();
        PageHelper.startPage(currentPage,pageSize);
        List<RepairDto> list = repairService.repairList(id,no,recorder);
        PageInfo<RepairDto> pageInfo = new PageInfo<>(list);
        return Result.succ(pageInfo);
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
