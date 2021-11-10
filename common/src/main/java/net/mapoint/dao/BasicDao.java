package net.mapoint.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BasicDao<E> {

    final SessionFactory sessionFactory;
    private Class<E> type;

    BasicDao(Class<E> type, SessionFactory sessionFactory) {
        this.type = type;
        this.sessionFactory = sessionFactory;
    }

    public E add(E e) {
        Session session = sessionFactory.getCurrentSession();
        session.save(e);
        return e;
    }

    public E get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(type, id);
    }

    public List<E> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from " + type.getSimpleName(), type)
            .list();
    }

    public E update(E e) {
        Session session = sessionFactory.getCurrentSession();
        Object merged = session.merge(e);
        session.update(merged);
        return e;

    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        E object = session.load(type, id);
        session.delete(object);
    }
}