package com.bill.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class ContactRepository {
    private final List<Contact> contacts;

    public ContactRepository() {
        contacts = createAll();
    }

    private static List<RawContact> getAllRaw() throws IOException {
        final URL resource = ContactRepository.class.getResource("/contacts.json");
        if (resource != null) {
            var iterator = new ObjectMapper().readerFor(RawContact.class).<RawContact>readValues(resource.openStream());
            return iterator.readAll();
        }
        return List.of();
    }

    public List<Contact> getAll() {
        return contacts;
    }

    private static List<Contact> createAll() {
        try {
            return getAllRaw().stream().map(RawContact::toContact).toList();
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
