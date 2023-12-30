package com.bill.server;

import java.util.UUID;

public record RawContact(String avatar, String first, String last, String twitter) {

    Contact toContact() {
        return new Contact(UUID.randomUUID().toString(), avatar, first, last, twitter, null, false);
    }
}

