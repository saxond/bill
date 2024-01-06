package com.bill.server;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RequestMapping(value = "/contacts", produces = "application/json")
@CrossOrigin
@RestController
public class ContactsController {

    private final ContactRepository contactRepository;

    public ContactsController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/")
    public List<Contact> index() {
        return StreamSupport.stream(contactRepository.findAll().spliterator(), false).toList();
    }

    @GetMapping("{id}")
    public Contact get(@PathVariable String id) {
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
        }).orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        contactRepository.deleteById(id);
    }
}
