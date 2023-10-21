package com.annunakicosmocrew.ushhak.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class DatabaseManager {
    private static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("ushak-pu");
    }

    private DatabaseManager() {
        // private constructor to prevent instantiation
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }


}
