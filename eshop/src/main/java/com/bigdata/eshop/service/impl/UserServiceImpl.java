package com.bigdata.eshop.service.impl;

import com.bigdata.eshop.dao.BaseDao;
import com.bigdata.eshop.model.User;
import com.bigdata.eshop.service.UserService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService {
    @Resource(name = "userDao")
    public void setDao(BaseDao<User> dao) {
        super.setDao(dao);
    }
}
