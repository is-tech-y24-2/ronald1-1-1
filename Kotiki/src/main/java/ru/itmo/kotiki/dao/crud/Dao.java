package ru.itmo.kotiki.dao.crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.itmo.kotiki.dao.session.SessionFactoryUtil;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class Dao <T>{

    private SessionFactory sessionFactory;
    private Class<T> type;

    public Dao(Class<T> type){
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        this.type = type;
    }

    public T findById(int id){
        Session session = sessionFactory.openSession();
        T item = (T) session.get(type, id);
        session.close();
        return item;
    }
    public List<T> findAll(){
        Session session = sessionFactory.openSession();
        CriteriaQuery criteriaQuery = session.getCriteriaBuilder().createQuery(type);
        criteriaQuery.from(type);
        List<T> items = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return items;
    }

    public void save(T item){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
    }
    public void delete(T item){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(item);
        transaction.commit();
        session.close();
    }
    public void update(T item){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        transaction.commit();
        session.close();
    }
}
