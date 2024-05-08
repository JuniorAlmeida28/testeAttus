package com.attus.attusvaga.controller;

import com.attus.attusvaga.dto.AddressRecordDto;
import com.attus.attusvaga.model.Address;
import com.attus.attusvaga.repositories.AddressRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @PostMapping("/address")
    public ResponseEntity<Address> saveAddress(@RequestBody @Valid AddressRecordDto addressRecordDto){
        var address = new Address();
        BeanUtils.copyProperties(addressRecordDto, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressRepository.save(address));
    }

    @GetMapping("/{idPessoa}")
    public ResponseEntity<List<AddressRecordDto>> listingAllAddressesPerPerson(@PathVariable UUID idPerson) {
        List<Address> addresses = service.searchAllAddressesById(idPerson);
        List<AddressRecordDt> adressRecordDto = addresses.stream()
                .map(AddressRecordDt::new)
                .collect(Collectors.toList());
        if (AddressRecordDt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AddressRecordDt);
    }

    @GetMapping("/{idPessoa}/{id}")
    public ResponseEntity<AddressRecordDt> listingAddressByPerson(@PathVariable UUID idPerson, @PathVariable UUID id) {
        Address addresses = service.findAddressesByPersonId(idPerson, id);
        if(addresses.getId() != null || addresses.getperson().getId() != null) {
            return ResponseEntity.ok(new AddressRecordDt(addresses));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressRecordDt> updateaddresse(@PathVariable Long id, @RequestBody AddressRecordDt dto) {
        Optional<Address> addresseById = service.findById(id);
        if(addresseById.isPresent()) {
            Address enderecos = addresseById.get();
            address.updateData(dto);
            service.update(address);
            return ResponseEntity.ok(new AddressRecordDt(addresse));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
