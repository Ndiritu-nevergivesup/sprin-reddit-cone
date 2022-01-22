package com.ndirituedwin;

import org.testcontainers.containers.PostgreSQLContainer;

public class BaseTest {
   static PostgreSQLContainer postgreSQLContainer=(PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("spring-reddit-testdb")
            .withUsername("testUser")
            .withPassword("pass");


   static {
       postgreSQLContainer.start();
   }
}
