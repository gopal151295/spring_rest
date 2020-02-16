package com.springapp.spring_project.dao.impl;

import com.springapp.spring_project.dao.UserDAO;
import com.springapp.spring_project.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDAOHibernateImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
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
}
