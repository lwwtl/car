package com.rlw.service.impl;

import com.rlw.common.dto.RepairDto;
import com.rlw.entity.Repair;
import com.rlw.mapper.RepairMapper;
import com.rlw.service.RepairService;
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
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {

    @Autowired(required = false)
    private RepairMapper repairMapper;

    @Override
    public List<RepairDto> repairList(Long id, String no, String recorder) {
        return repairMapper.repairList(id,no,recorder);
    }
}
