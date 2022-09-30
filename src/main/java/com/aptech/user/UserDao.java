package com.aptech.user;

import com.aptech.exception.UserException;
import com.aptech.utils.AESUtil;
import com.aptech.utils.JPAUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    private static UserDao instance;
    private final static Logger LOGGER = LogManager.getLogger(UserDao.class);

    private UserDao() {
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }


    public List<User> getAll() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        List<User> users = new ArrayList<>();
        try {
            Query query = em.createQuery("select u from User u order by u.createdDate desc", User.class);
            users = query.getResultList();
            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return users;
    }

    public Optional<User> get(long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        User user = null;
        try {
            user = em.find(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Optional.ofNullable(user);
    }

    public void save(User user) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void update(User user) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        Optional<User> user = this.get(id);
        if (!user.isPresent()) {
            throw new UserException("Not found user with id " + id);
        }
        try {
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new UserException("Error delete user " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public User login(String username, String password) {
        User user = null;
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            Query query = em.createQuery("select u from User u where u.username =:username and u.password=:password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", AESUtil.encrypt(password));
            List<User> users = query.getResultList();
            if (users != null && users.size() > 0) {
                user = users.get(0);
            }
        } catch (Exception e) {
            LOGGER.error("Error login", e);
        }
        return user;
    }
}
