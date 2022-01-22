package com.ndirituedwin.repository;

import com.ndirituedwin.BaseTest;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Repository.PostRepository;
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
public class PostRepositoryTest  extends BaseTest {
//    @Container
//    PostgreSQLContainer postgreSQLContainer=(PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
//            .withDatabaseName("spring-reddit-testdb")
//            .withUsername("testUser")
//            .withPassword("pass");
    @Autowired
    PostRepository postRepository;
    @Test
    public void shouldSavePost(){
        Post expectedPostObject=new Post(null,"post one","url one","description one",121,null, Instant.now(),null);
        Post actualPostObject=postRepository.save(expectedPostObject);
        assertThat(actualPostObject).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedPostObject);
    }

}
