package com.attus.attusvaga.controller;


import com.attus.attusvaga.dto.PersonRecordDto;
import com.attus.attusvaga.model.Person;
import com.attus.attusvaga.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping("/person")
    public ResponseEntity<Person> savePerson(@RequestBody @Valid PersonRecordDto personRecordDto){
        var person = new Person();
        BeanUtils.copyProperties(personRecordDto, person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(person));
    }

    @GetMapping("/person")
    public ResponseEntity<List<Person>> getAllPerson(){
        return ResponseEntity.status(HttpStatus.OK).body(personRepository.findAll());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Object> getOnePerson(@PathVariable(value="id") UUID id){
        Optional<Person> person0 = personRepository.findById(id);
        if (person0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(person0.get());
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value="id") UUID id, @RequestBody @Valid PersonRecordDto personRecordDto){
        Optional<Person> person0 = personRepository.findById(id);
        if (person0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        var person = person0.get();
        BeanUtils.copyProperties(personRecordDto, person);
        return ResponseEntity.status(HttpStatus.OK).body(personRepository.save(person));
    }
}
