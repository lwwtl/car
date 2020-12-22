package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.lang.Result;
import com.rlw.entity.Employee;
import com.rlw.service.EmployeeService;
import com.rlw.util.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public Result index(@PathVariable("id") Long id){
        Employee employee = employeeService.getById(id);
        System.out.println(employee);
        return Result.succ(200,"操作成功",employee);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "5") Integer pageSize){

        Page page = new Page(currentPage,pageSize);
        //按时间倒序查询
        IPage pageData = employeeService.page(page, new QueryWrapper<Employee>().orderByDesc("emp_last_time"));

        return Result.succ(pageData);
    }

    @PostMapping("/add")
    public Result register(@RequestBody Employee employee) {
        //明文密码进行md5+sale+hash散列
        //1、生成随机盐
        String salt = SaltUtils.getSalt(8);
        //2、将随机盐保存到数据
        employee.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(),salt,1024);
        employee.setPassword(md5Hash.toHex());
        employee.setRole("员工");
        employeeService.save(employee);
        return Result.succ(null);
    }
}
