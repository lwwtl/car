package com.rlw.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.rlw.entity.Employee;
import com.rlw.service.EmployeeService;
import com.rlw.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmployeeService employeeService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwt = (JwtToken) token;
        log.info("jwt----------------->{}", jwt);
        /*拿到用户信息*/
        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();


        Employee employee = employeeService.getById(Long.parseLong(userId));
        if(employee == null) {
            throw new UnknownAccountException("账户不存在！");
        }
//        if(employee.getStatus() == -1) {
//            throw new LockedAccountException("账户已被锁定！");
//        }
        /*返回给shiro*/
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(employee, profile);
        log.info("profile----------------->{}", profile.toString());
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}
