package com.ndirituedwin.repository;

import com.ndirituedwin.BaseTest;
import com.ndirituedwin.Model.User;
import com.ndirituedwin.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends BaseTest {
//    @Container
//    PostgreSQLContainer postgreSQLContainer=(PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
//            .withDatabaseName("spring-reddit-testdb")
//            .withUsername("testUser")
//            .withPassword("pass");

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldSaveUser(){
        User expecteduserObject=new User(null,"test user","secret password","email@gmail.com", Instant.now(),true);
        User actualUserObject=userRepository.save(expecteduserObject);
        assertThat(actualUserObject).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(expecteduserObject);
    }
}
