package com.bigdata.eshop.dao;

import java.util.List;
/*
*  BaseDao 接口
* */
public interface BaseDao<T> {
    public void saveEntity(T t);
    public void updateEntity(T t);
    public void saveOrUpdateEntity(T t);
    public void deleteEntity(T t);
    public T getEntity(Integer id);

    /*按照HQL*/
    public List<T> findByHQL(String hql, Object... objects);
    public void  execHQL(String hql,Object... objects);
}
