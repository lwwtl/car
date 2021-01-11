package com.rlw.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rlw.common.lang.Result;
import com.rlw.entity.User;
import com.rlw.service.UserService;
import com.rlw.util.JwtUtils;
import com.rlw.util.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 饶立玮
 * @since 2021-01-08
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JavaMailSender mailSender;//一定要用@Autowired

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;



    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestParam Integer userState,
                       @RequestBody User user){
        Page page = new Page(currentPage,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String name = user.getName();
        String mobile = user.getMobile();
        if(!StringUtils.isEmpty(userState)) {
            queryWrapper.eq("user_state",userState);
        }
        if(StringUtils.isEmpty(name)&&StringUtils.isEmpty(mobile)){
            IPage pageData = userService.page(page, queryWrapper.orderByDesc("user_id"));
            return Result.succ(pageData);
        }else {
            if (!StringUtils.isEmpty(name)){
                queryWrapper.like("user_name",user.getName());
            }
            if (!StringUtils.isEmpty(mobile)){
                queryWrapper.like("user_mobile",user.getMobile());
            }
            IPage pageData = userService.page(page,queryWrapper.orderByDesc("user_id"));
            return Result.succ(pageData);
        }
    }

    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody User user) {
        //验证码校验，查询发送给用户的验证码与输入的是否一致
        String code = (String) redisTemplate.opsForValue().get("user:"+user.getEmail());
        if(!code.equals(user.getCode())){
            return Result.fail("验证码错误");
        }
        //创建新用户时，只要存在重复账号则无法创建
        User repeatAccount = userService.getOne(new QueryWrapper<User>().eq("user_account",user.getAccount()));
        if(repeatAccount != null && user.getId()==null){
            return Result.fail("该帐号已存在");
        }
        if(user.getId() != null){
//                帐号存在时更新
                userService.saveOrUpdate(user);
        }else {
            //明文密码进行md5+sale+hash散列
            //1、生成随机盐
            String salt = SaltUtils.getSalt(8);
            //2、将随机盐保存到数据
            user.setSalt(salt);
            Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
            user.setPassword(md5Hash.toHex());
            user.setState(1L);
            userService.save(user);
        }
        return Result.succ(null);
    }

    @GetMapping("/find/{id}")
    public Result find(@PathVariable(name = "id")Long id){
        User user = userService.getById(id);
        Assert.notNull(user,"该用户已被删除");
        return Result.succ(user);
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable(name = "id")Long id){
        User user = userService.getById(id);
        Assert.notNull(user,"该用户已被删除");
        userService.removeById(id);
        return Result.succ(null);
    }

    @GetMapping("/changeUserState/{id}")
    public Result changeUserState(@PathVariable(name = "id")Long id){
        User user = userService.getById(id);
        Assert.notNull(user,"该用户已被删除");
        if(user.getState()==1){
            user.setState(0L);
        }else {
            user.setState(1L);
        }
        userService.saveOrUpdate(user);
        return Result.succ(null);
    }


    /**
     * 给前端输入的邮箱，发送验证码
     * @param email
     * @param
     * @return
     */

    @PostMapping("/sendEmail/{email}")
    public Result sendEmail(@PathVariable(name = "email")String email){

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证码邮件");//主题
            //生成随机数
            String code = randomCode();
            //将随机数放置到redis中
            redisTemplate.opsForValue().set("user:"+email,code);
            redisTemplate.expire("user:"+email,300, TimeUnit.SECONDS);
            mailMessage.setText("您收到的验证码是： "+code+"  (5分钟内有效) ");//内容
            mailMessage.setTo(email);//发给谁
            mailMessage.setFrom(from);//你自己的邮箱
            mailSender.send(mailMessage);//发送
            return Result.succ(null);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(null);
        }
    }

    /**
     * 随机生成6位数的验证码
     * @return String code
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
