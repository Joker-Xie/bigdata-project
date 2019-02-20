package com.bigdata.eshop.service;

import com.bigdata.eshop.model.User;

public interface UserService extends BaseService<User>{
    public boolean isRegisted(String email);
}
