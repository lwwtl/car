package com.rlw.service.impl;

import com.rlw.common.dto.MyViolationsDto;
import com.rlw.common.dto.ViolationsDto;
import com.rlw.entity.Violations;
import com.rlw.mapper.OrderMapper;
import com.rlw.mapper.ViolationsMapper;
import com.rlw.service.ViolationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 饶立玮
 * @since 2021-02-04
 */
@Service
public class ViolationsServiceImpl extends ServiceImpl<ViolationsMapper, Violations> implements ViolationsService {

    @Autowired(required = false)
    private ViolationsMapper violationsMapper;

    @Override
    public List<MyViolationsDto> findMyViolations(Long id) {
        return violationsMapper.findMyViolations(id);
    }

    @Override
    public List<ViolationsDto> violationsList(Long id, String no, String recorder) {
        return violationsMapper.violationsList(id,no,recorder);
    }
}
