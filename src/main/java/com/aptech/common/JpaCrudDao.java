package com.aptech.common;

import com.aptech.exception.UserException;
import com.aptech.utils.JPAUtil;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class JpaCrudDao<T extends AbstractEntity<Long>, Long extends Serializable> {
    private Class<T> model;

    public JpaCrudDao(Class<T> model) {
        this.model = model;
    }

    public abstract List<T> getAll();

    public Optional<T> getById(long id) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        T entity = null;
        try {
            entity = em.find(this.model, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Optional.ofNullable(entity);
    }

    public void create(T entity) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void update(T entity) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entity);
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
        Optional<T> entity = this.getById(id);
        if (!entity.isPresent()) {
            throw new UserException("Not found entity with id " + id);
        }
        try {
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new UserException("Error delete entity " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
