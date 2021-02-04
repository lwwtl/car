package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.lang.Result;
import com.rlw.entity.Repair;
import com.rlw.entity.Violations;
import com.rlw.service.RepairService;
import com.rlw.service.ViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "5") Integer pageSize, @RequestBody Violations violations) {
        Page page = new Page(currentPage, pageSize);
        QueryWrapper<Violations> queryWrapper = new QueryWrapper<>();
        String orderId = violations.getOrderId().toString();
        if (StringUtils.isEmpty(orderId)) {
            IPage pageData = violationsService.page(page, new QueryWrapper<Violations>().orderByDesc("violations_create"));
            return Result.succ(pageData);
        } else {
            queryWrapper.eq("order_id", violations.getOrderId());
            IPage pageData = violationsService.page(page, queryWrapper.orderByDesc("violations_create"));
            return Result.succ(pageData);
        }
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Violations violations) {
        if (violations.getViolationsId() != null) {
            violationsService.saveOrUpdate(violations);
        }else {
            violationsService.save(violations);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id") Long id) {
        QueryWrapper<Violations> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        Violations violations = violationsService.getOne(queryWrapper);
        return Result.succ(violations);
    }
}
