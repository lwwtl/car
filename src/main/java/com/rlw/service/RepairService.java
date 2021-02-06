package com.rlw.service;

import com.rlw.common.dto.RepairDto;
import com.rlw.entity.Repair;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 饶立玮
 * @since 2021-02-04
 */
public interface RepairService extends IService<Repair> {
    List<RepairDto> repairList(Long id,String no, String recorder);

}
