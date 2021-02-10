package com.rlw.service;

import com.rlw.entity.Store;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-06
 */
public interface StoreService extends IService<Store> {
    String storeNum();
}
