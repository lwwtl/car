package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.lang.Result;
import com.rlw.entity.Car;
import com.rlw.entity.Store;
import com.rlw.mapper.StoreMapper;
import com.rlw.service.StoreService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired(required = false)
    StoreMapper storeMapper;

    @Autowired
    StoreService storeService;

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "5") Integer pageSize, @RequestBody Store store) {
        Page page = new Page(currentPage, pageSize);
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        String name = store.getName();
        String address = store.getAddress();
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(address)) {
            IPage pageData = storeService.page(page, new QueryWrapper<Store>().orderByDesc("store_id"));
            return Result.succ(pageData);
        } else {
            if (!StringUtils.isEmpty(name)) {
                queryWrapper.like("store_name", store.getName());
            }
            if (!StringUtils.isEmpty(address)) {
                queryWrapper.like("store_address", store.getAddress());
            }
            IPage pageData = storeService.page(page, queryWrapper.orderByDesc("store_id"));
            return Result.succ(pageData);
        }
    }



    @PostMapping("/edit")
    public Result edit(@RequestBody Store store) {
        Store repeatName = storeService.getOne(new QueryWrapper<Store>().eq("store_name", store.getName()));
        if (repeatName != null && store.getId() == null) {
            return Result.fail("该车牌号已存在");
        }else if (repeatName != null && !repeatName.getId().equals(store.getId())) {
            return Result.fail("该车牌号已存在");
        }else if (store.getId() != null) {
            storeService.saveOrUpdate(store);
        }else {
            storeService.save(store);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id") Long id) {
        Store store = storeService.getById(id);
        Assert.notNull(store, "该门店已被删除");
        return Result.succ(store);
    }

    @PostMapping("/findStore")
    public Result findStore(@RequestParam(name = "region") String region) {
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.eq("store_address",region);
        List<Store> storeList = storeMapper.selectList(wrapper);
        return Result.succ(storeList);
    }

    @PostMapping("/findStoreByName")
    public Result findStoreByName(@RequestParam(name = "storeName") String storeName) {
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.eq("store_name",storeName);
        Store store = storeService.getOne(wrapper);
        return Result.succ(store);
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id") Long id) {
        Store store = storeService.getById(id);
        Assert.notNull(store, "该门店已被删除");
        storeService.removeById(id);
        return Result.succ(null);
    }
}
