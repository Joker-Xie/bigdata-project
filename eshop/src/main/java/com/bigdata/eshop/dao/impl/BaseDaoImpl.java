package com.bigdata.eshop.dao.impl;

import com.bigdata.eshop.dao.BaseDao;
import org.hibernate.SessionFactory;
import org.hibernate.*;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    private Class<T> clazz;
    /*
     * hibernate会话工厂，相当于连接池，数据源。
     * */
    private SessionFactory sf;

    @Resource(name = "sf")
    public void setSf(SessionFactory sf) {
        this.sf = sf;
    }

    public BaseDaoImpl() {
        //取得子类的泛型化超类
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //得到第一个实际参数
        clazz = (Class) type.getActualTypeArguments()[0];

    }

    public void saveEntity(T t) {
        sf.getCurrentSession().save(t);
    }

    public void updateEntity(T t) {
        sf.getCurrentSession().update(t);
    }

    public void saveOrUpdateEntity(T t) {
        sf.getCurrentSession().saveOrUpdate(t);
    }

    public void deleteEntity(T t) {
        sf.getCurrentSession().delete(t);

    }

    //按照id查询对象实体
    public T getEntity(Integer id) {
        return (T) sf.getCurrentSession().get(clazz, id);
    }


    /*
     * 按照hql 查询数据
     * */
    public List<T> findByHQL(String hql, Object... objects) {
        Query q = sf.getCurrentSession().createQuery(hql);
        for (int i = 0; i < objects.length; i++) {
            q.setParameter(i, objects[i]);
        }
        return q.list();
    }

    /*
     * 按照hql 进行批量写操作
     * */
    public void execHQL(String hql, Object... objects) {
        Query q = sf.getCurrentSession().createQuery(hql);
        for (int i = 0; i < objects.length; i++) {
            q.setParameter(i, objects[i]);
        }
        q.executeUpdate();
    }
}
