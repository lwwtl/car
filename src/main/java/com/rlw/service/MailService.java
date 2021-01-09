package com.rlw.service;


import ch.qos.logback.core.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;//一定要用@Autowired

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private UserMapper userMapper;//注入UserMapper，交给bena

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 给前端输入的邮箱，发送验证码
     * @param email
     * @param
     * @return
     */
    public boolean sendMimeMail( String email) {
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
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
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

}