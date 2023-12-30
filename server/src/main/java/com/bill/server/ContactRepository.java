package com.bill.server;

import org.springframework.data.repository.CrudRepository;


public interface ContactRepository extends CrudRepository<Contact, String> {
}
