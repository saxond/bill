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

    @Test
    void insert() {
        var all = new ContactRepository().getAll();
        all.forEach(contact -> {
            var builder = new StringBuilder();
            builder.append("INSERT into contacts(id, avatar, first, last, twitter) VALUES (");
            builder.append(quote(contact.id()))
                    .append(',')
                    .append(quote(contact.avatar()))
                    .append(',')
                    .append(quote(contact.first()))
                    .append(',')
                    .append(quote(contact.last()))
                    .append(',')
                    .append(quote(contact.twitter()))
                    .append(");");
            System.out.println(builder.toString());
        });
    }

    static String quote(String value) {
        if (value == null) {
            return "null";
        }
        return '"' + value + '"';
    }
}