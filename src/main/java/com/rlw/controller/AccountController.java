package com.rlw.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rlw.common.dto.LoginDto;
import com.rlw.common.lang.Result;
import com.rlw.entity.Employee;
import com.rlw.service.EmployeeService;
import com.rlw.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
public class AccountController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){

        Employee employee = employeeService.getOne(new QueryWrapper<Employee>().eq("emp_account", loginDto.getAccount()));
        //断言
        Assert.notNull(employee,"用户不存在");
        Md5Hash md5Hash = new Md5Hash(loginDto.getPassword(), employee.getSalt(),1024);

        if(!employee.getPassword().equals(md5Hash.toHex())){
//        if(!employee.getEPassword().equals(loginDto.getPassword())){
            return Result.fail("密码不正确");
        }
        //更新登录时间
        employee.setLastTime(LocalDateTime.now());
        employeeService.saveOrUpdate(employee);
        //生成token
        String jwt = jwtUtils.generateToken(employee.getId());
        //把jwt放入到请求头中
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id",employee.getId())
                .put("name",employee.getName())
                .put("account",employee.getAccount())
                .put("role",employee.getRole())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
