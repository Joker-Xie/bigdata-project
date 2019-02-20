package com.bigdata.eshop.service.impl;

import com.bigdata.eshop.dao.BaseDao;
import com.bigdata.eshop.service.BaseService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/*
 * BaseService 抽象类
 * */
// 注解驱动：@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    private BaseDao<T> dao;
    private Class<T> clazz;

    public BaseServiceImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class) type.getActualTypeArguments()[0];
    }

    public BaseDao<T> getDao() {
        return dao;
    }

    public void setDao(BaseDao<T> dao) {
        this.dao = dao;
    }

    public void saveEntity(T t) {
        dao.saveEntity(t);
    }

    public void updateEntity(T t) {
        dao.updateEntity(t);
    }

    public void saveOrUpdateEntity(T t) {
        dao.saveOrUpdateEntity(t);
    }

    public void deleteEntity(T t) {
        dao.deleteEntity(t);
    }

    public T getEntity(Integer id) {
        return dao.getEntity(id);
    }

    public List<T> findByHQL(String hql, Object... objects) {
        return dao.findByHQL(hql, objects);
    }

    public void execHQL(String hql, Object... objects) {
        dao.execHQL(hql, objects);
    }

    public List<T> findAllEntites() {
        String hql = "from " +clazz.getSimpleName();
        return this.findByHQL(hql);
    }
}
