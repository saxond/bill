package com.bill.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactsControllerTest {
    @Autowired
    ContactsController controller;

    @Test
    void create() {
        var contact = new Contact();
        contact.setFirst("Bill");
        contact.setLast("Person");
        var created = controller.create(contact);
        assertNotNull(created.getId());

        var found = controller.get(created.getId());
        assertEquals(created.getId(), found.getId());
    }

    @Test
    void index() {
        var all = controller.index();
        assertNotNull(all);
    }
}