package com.bill.server;

public record Contact(String id, String avatar, String first, String last, String twitter, String notes, boolean favorite) {
}
