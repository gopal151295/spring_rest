package com.springapp.spring_project.dao.impl;

import com.springapp.spring_project.dao.UserDAO;
import com.springapp.spring_project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoJpaImpl implements UserDAO {
    private EntityManager entityManager;

    @Autowired
    public UserDaoJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List findAll() {
        Query query = entityManager.createQuery("from User");

        return query.getResultList();
    }

    @Override
    public User findById(int id) {
        // get the user
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        User savedUser = entityManager.merge(user);

        user.setId(savedUser.getId());
    }

    @Override
    public void deleteById(int id) {
        Query query = entityManager.createQuery("delete from User where id=:userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }
}
