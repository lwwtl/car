package com.rlw.mapper;

import com.rlw.common.dto.MyOrderDto;
import com.rlw.common.dto.MyViolationsDto;
import com.rlw.common.dto.ViolationsDto;
import com.rlw.entity.Violations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 饶立玮
 * @since 2021-02-04
 */
public interface ViolationsMapper extends BaseMapper<Violations> {
    List<MyViolationsDto> findMyViolations(@Param("id")Long id);
    List<ViolationsDto> violationsList(@Param("id")Long id,@Param("no")String no,@Param("recorder") String recorder);
}
