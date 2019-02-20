package com.bigdata.eshop.service.impl;

import com.bigdata.eshop.dao.BaseDao;
import com.bigdata.eshop.model.User;
import com.bigdata.eshop.service.UserService;
import com.bigdata.eshop.util.ValidataUtil;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService {
    @Resource(name = "userDao")
    public void setDao(BaseDao<User> dao) {
        super.setDao(dao);
    }
    //实现注册判断
    public boolean isRegisted(String email) {
        String hql = "from User u where u.email = ?";
        List<User> list = this.findByHQL(hql,email);
        return ValidataUtil.isValid(list);
    }
}
