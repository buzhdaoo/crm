package com.bjpow.crm.settings.service.impl;

import com.bjpow.crm.exception.LoginException;
import com.bjpow.crm.settings.dao.UserDao;
import com.bjpow.crm.settings.domain.User;
import com.bjpow.crm.settings.service.UserService;
import com.bjpow.crm.utils.DateTimeUtil;
import com.bjpow.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao= SqlSessionUtil.getSqlSession ().getMapper (UserDao.class);

    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String>map=new HashMap<> ();
        map.put ("loginPwd",loginPwd);
        map.put ("loginAct",loginAct);
        User user= userDao.login(map);
        if (user==null){
            throw new LoginException ("账号密码错误");
        }
        //如果程序能成功的执行到这里
        //需要验证其他3项信息
        //判断失效时间
        String exprieTime=user.getCreateTime ();
        String currentTime= DateTimeUtil.getSysTime ();
       // int nums=exprieTime.compareTo (currentTime);
        //System.out.println ("nums=============="+   nums );
        if (exprieTime.compareTo (currentTime)>0){
            throw new LoginException ("账号已失效");
        }
        //判断锁定状态
        String lockState=user.getLockState ();
        if ("0".equals (lockState)){
            throw new LoginException ("账号已锁定");

        }//判断IP地址
        String allowIps=user.getAllowIps ();
        if (allowIps.contains (ip)){
            throw new LoginException ("ip地址受限");
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> ulist=userDao.getUserList();
        return ulist;
    }
}
