package com.rlw.service;

import com.rlw.common.dto.MyViolationsDto;
import com.rlw.common.dto.ViolationsDto;
import com.rlw.entity.Violations;
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
public interface ViolationsService extends IService<Violations> {
    List<MyViolationsDto> findMyViolations(Long id);
    List<ViolationsDto> violationsList(Long id, String no, String recorder);
}
