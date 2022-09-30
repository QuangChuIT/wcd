package com.aptech.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private final static String PERSISTENCE_UNIT_NAME = "wcd-persistence";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
