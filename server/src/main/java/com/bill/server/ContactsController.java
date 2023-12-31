package com.bill.server;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RequestMapping(value = "/contacts", produces = "application/json")
@RestController
public class ContactsController {

    private final ContactRepository contactRepository;

    public ContactsController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/")
    public List<Contact> index() {
        System.err.println("Getall");
        return StreamSupport.stream(contactRepository.findAll().spliterator(), false).toList();
    }

    @GetMapping("{id}")
    public Contact get(@PathVariable String id) {
        System.err.println("Get " + id);
        return contactRepository.findById(id).orElseThrow();
    }

    @PostMapping("/")
    public Contact create(@RequestBody Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        return contactRepository.save(contact);
    }
    
    @PutMapping("{id}")
    public Contact update(@RequestBody Contact contact, @PathVariable String id) {
        return contactRepository.findById(id).map(existing -> {
            contact.setId(id);
            return contactRepository.save(contact);
        }).orElseGet(() -> create(contact));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        System.err.println("Delete " + id);
        contactRepository.deleteById(id);
    }
}
