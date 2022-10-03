package com.aptech.product;

import com.aptech.common.JpaCrudDao;
import com.aptech.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends JpaCrudDao<Product, Long> {

    private static ProductDao instance;

    public ProductDao() {
        super(Product.class);
    }

    public static ProductDao getInstance() {
        if (instance == null) {
            instance = new ProductDao();
        }
        return instance;
    }

    @Override
    public List<Product> getAll() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        List<Product> products = new ArrayList<>();
        try {
            Query query = em.createQuery("select p from Product p", Product.class);
            products = query.getResultList();
            if (products == null) {
                products = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return products;
    }
}
