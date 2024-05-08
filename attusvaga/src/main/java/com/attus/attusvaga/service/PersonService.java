package com.attus.attusvaga.service;

import com.attus.attusvaga.model.Person;
import com.attus.attusvaga.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public class PersonService {

    private final PersonRepository personRepository;

    public void PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person create(Person person) {
        return this.personRepository.save(person);
    }

    public Person findOne(UUID id) {
        return this.personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person with id %d was not found".formatted(id)));
    }

    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    public List<Person> findAllPageable(int page) {
        var size = 5;
        var pageable = PageRequest.of(page, size);
        Page<Person> pagePerson = this.personRepository.findAll(pageable);
        return pagePerson.getContent();
    }

    @Transactional
    public Person update(UUID id, Person newPerson) {
        var oldPerson = this.findOne(id);
        oldPerson.setFullName(newPerson.getFullName());
        oldPerson.setBirthDate(newPerson.getBirthDate());
        return this.personRepository.save(oldPerson);
    }
}
