package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.lang.Result;
import com.rlw.entity.Employee;
import com.rlw.service.EmployeeService;
import com.rlw.util.SaltUtils;
import freemarker.template.utility.StringUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 饶立玮
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    //需要登录才能访问    @RequiresAuthentication
//    @GetMapping("/{id}")
//    public Result index(@PathVariable("id") Long id){
//        Employee employee = employeeService.getById(id);
//        System.out.println(employee);
//        return Result.succ(200,"操作成功",employee);
//    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "5") Integer pageSize,@RequestBody Employee employee){
        Page page = new Page(currentPage,pageSize);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        String name = employee.getName();
        String mobile = employee.getMobile();
        if(StringUtils.isEmpty(name)&&StringUtils.isEmpty(mobile)){
            IPage pageData = employeeService.page(page, new QueryWrapper<Employee>().orderByDesc("emp_last_time"));
            return Result.succ(pageData);
        }else {
            if (!StringUtils.isEmpty(name)){
                queryWrapper.like("emp_name",employee.getName());
            }
            if (!StringUtils.isEmpty(mobile)){
                queryWrapper.like("emp_mobile",employee.getMobile());
            }
            IPage pageData = employeeService.page(page,queryWrapper.orderByDesc("emp_last_time"));
            return Result.succ(pageData);
        }
    }

    @RequiresRoles("a")//用来判断角色
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Employee employee) {
        Employee repeatAccount = employeeService.getOne(new QueryWrapper<Employee>().eq("emp_account",employee.getAccount()));
        //创建新用户时，只要存在重复账号则无法创建
        if(repeatAccount != null && employee.getId()==null){
            return Result.fail("该帐号已存在");
        }
        //当传入的修改account非当前用户登录account，并且该account已经存在数据库，禁止修改
        if(repeatAccount != null && !repeatAccount.getId().equals(employee.getId())){
            return Result.fail("该帐号已存在");
        }
        if(employee.getId() != null){
            Employee temp = employeeService.getById(employee.getId());
            if(employee.getPassword().equals(temp.getPassword())){
                employeeService.saveOrUpdate(employee);
            }else {
                String salt = SaltUtils.getSalt(8);
                employee.setSalt(salt);
                Md5Hash md5Hash = new Md5Hash(employee.getPassword(),salt,1024);
                employee.setPassword(md5Hash.toHex());
                employeeService.saveOrUpdate(employee);
            }
        }else {
            //明文密码进行md5+sale+hash散列
            //1、生成随机盐
            String salt = SaltUtils.getSalt(8);
            //2、将随机盐保存到数据
            employee.setSalt(salt);
            Md5Hash md5Hash = new Md5Hash(employee.getPassword(),salt,1024);
            employee.setPassword(md5Hash.toHex());
            employeeService.save(employee);
        }
        return Result.succ(null);
    }


    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id")Long id){
        Employee employee = employeeService.getById(id);
        Assert.notNull(employee,"该用户已被删除");
        return Result.succ(employee);
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id")Long id){
        Employee employee = employeeService.getById(id);
        Assert.notNull(employee,"该用户已被删除");
        employeeService.removeById(id);
        return Result.succ(null);
    }
}
