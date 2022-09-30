package com.aptech.category;

import com.aptech.common.GenericDao;
import com.aptech.utils.JPAUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class CategoryDao implements GenericDao<Category> {
    @Override
    public List<Category> getAll() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        Query query = em.createQuery("select c from Category c", Category.class);
        List<Category> categories = query.getResultList();
        return categories;
    }

    @Override
    public Optional<Category> getById(long id) {
        return Optional.empty();
    }

    @Override
    public void create(Category category) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error("Error create category", e);
        }
    }

    @Override
    public void update(Category o) {

    }

    @Override
    public void delete(Category o) {

    }

    private final static Logger LOGGER = LogManager.getLogger(CategoryDao.class);
}
