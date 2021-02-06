package com.rlw.mapper;

import com.rlw.common.dto.RepairDto;
import com.rlw.common.dto.ViolationsDto;
import com.rlw.entity.Repair;
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
public interface RepairMapper extends BaseMapper<Repair> {
    List<RepairDto> repairList(@Param("id")Long id, @Param("no")String no, @Param("recorder") String recorder);
}
