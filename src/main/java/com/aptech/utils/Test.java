package com.aptech.utils;

import com.aptech.category.Category;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            Category category = em.find(Category.class, 5L);
            em.remove(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        Query query = em.createQuery("select c from Category c", Category.class);
        List<Category> categories = query.getResultList();
        for (Category category : categories) {
            System.out.println(category.getId() + " - " + category.getTitle() + " - " + category.getSlug());
        }
    }
}
