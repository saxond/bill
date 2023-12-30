package com.bill.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ContactRepositoryTest {
    @Test
    void getAll() {
        var all = new ContactRepository().getAll();
        assertEquals(31, all.size());
    }

    @Test
    void get() {
        final ContactRepository contactRepository = new ContactRepository();
        var all = contactRepository.getAll();
        assertNotNull(contactRepository.get(all.get(0).id()));
    }
}