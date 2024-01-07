package com.bill.server.contacts;

import com.bill.server.contacts.ContactRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This test depends on a running instance of the db container.
 */
@SpringBootTest
class ContactRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    ContactRepository repository;

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(repository);
    }

    @Test
    void getAll() {
        var all = StreamSupport.stream(repository.findAll().spliterator(), false).toList();
        assertEquals(31, all.size());
    }

    @Test
    void get() {
        assertNotNull(repository.findById("db8b422c-31af-4ac1-9f60-c2f1a01bc9a1"));
    }
}