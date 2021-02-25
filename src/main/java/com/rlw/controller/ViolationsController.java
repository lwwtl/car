package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rlw.common.dto.MyOrderDto;
import com.rlw.common.dto.MyViolationsDto;
import com.rlw.common.dto.ViolationsDto;
import com.rlw.common.lang.Result;
import com.rlw.entity.Repair;
import com.rlw.entity.Violations;
import com.rlw.service.RepairService;
import com.rlw.service.ViolationsService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
@RequestMapping("/violations")
public class ViolationsController {
    @Autowired
    ViolationsService violationsService;


    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestBody ViolationsDto violationsDto) {
        Long id = violationsDto.getOrderId();
        String no = violationsDto.getCarNo();
        String recorder = violationsDto.getViolationsRecorder();
        PageHelper.startPage(currentPage,pageSize);
        List<ViolationsDto> list = violationsService.violationsList(id,no,recorder);
        PageInfo<ViolationsDto> pageInfo = new PageInfo<>(list);
        return Result.succ(pageInfo);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Violations violations) {
        if (violations.getViolationsId() != null) {
            violations.setViolationsCreate(LocalDateTime.now());
            violationsService.saveOrUpdate(violations);
        }else {
            violationsService.save(violations);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id") Long id) {
        QueryWrapper<Violations> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",id);
        Violations violations = violationsService.getOne(queryWrapper);
        return Result.succ(violations);
    }


    @GetMapping("/myViolationsList/{id}")
    public Result myOrderList(@RequestParam(defaultValue = "1") Integer currentPage,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @PathVariable(name = "id") Long id) {

        PageHelper.startPage(currentPage,pageSize);
        List<MyViolationsDto> violationsList = violationsService.findMyViolations(id);
        PageInfo<MyViolationsDto> pageInfo = new PageInfo<>(violationsList);
        return Result.succ(pageInfo);
    }

}
