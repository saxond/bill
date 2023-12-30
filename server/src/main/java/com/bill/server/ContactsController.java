package com.bill.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/contacts", produces="application/json")
@RestController

public class ContactsController {

    private final ContactRepository contactRepository;

    public ContactsController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    
    @GetMapping("/")
    public List<Contact> index() {
        System.err.println("Getall");
        return contactRepository.getAll();
    }

    @GetMapping("{id}")
    public Contact get(@PathVariable String id) {
        System.err.println("Get " + id);
        return contactRepository.getAll().stream().filter(c -> c.id().equals(id)).findAny().orElseThrow();
    }
}
