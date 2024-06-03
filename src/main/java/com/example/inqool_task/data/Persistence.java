package com.example.inqool_task.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Persistence {
    private static final EntityManagerFactory factory =
            jakarta.persistence.Persistence.createEntityManagerFactory("inqool-task-pu");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
