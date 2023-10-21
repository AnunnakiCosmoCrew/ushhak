package com.annunakicosmocrew.ushhak.db;

import jakarta.persistence.EntityManager;

import java.io.Closeable;

public class EntityManagerWrapper implements Closeable {
    private final EntityManager entityManager;

    public EntityManagerWrapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void close() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
