package com.springapp.spring_project.dao.impl;

import com.springapp.spring_project.dao.UserDAO;
import com.springapp.spring_project.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAOHibernateImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        // get the current hibernate session
        Session session = entityManager.unwrap(Session.class);

        // create a query
        Query<User> query = session.createQuery("from User", User.class);

        // execute the query
        List<User> users = query.getResultList();

        // return the result
        return users;
    }

    @Override
    public User findById(int id) {
        // get the current session
        Session session = entityManager.unwrap(Session.class);

        // find user by id
        User user = session.get(User.class, id);

        // return the found user
        return user;
    }

    @Override
    public void save(User user) {
        // get the current session
        Session session = entityManager.unwrap(Session.class);

        // save the user
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteById(int id) {
        // get the current hibernate session
        Session session = entityManager.unwrap(Session.class);

        // create a query
        Query query = session.createQuery("delete from User where id=:userId");
        query.setParameter("userId", id);

        // execute the query
        query.executeUpdate();
    }
}
