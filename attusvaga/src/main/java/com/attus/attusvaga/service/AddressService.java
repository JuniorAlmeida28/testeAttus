package com.attus.attusvaga.service;

import com.attus.attusvaga.model.Address;
import com.attus.attusvaga.repositories.AddressRepository;
import com.attus.attusvaga.service.exception.MainAddressException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public class AddressService {
    private final AddressRepository addressRepository;

    private final PersonService personService;

    public AddressService(AddressRepository addressRepository, PersonService personService) {
        this.addressRepository = addressRepository;
        this.personService = personService;
    }

    @Transactional
    public Address create(Long idPerson, Address address) {
        var person = this.personService.findOne(idPerson);

        var addresses = this.findAll(idPerson);

        if (mainAddressAlreadyExists(addresses) && address.getMainAddress()) {
            throw new MainAddressException("Person with id %d already have a main address".formatted(idPerson));
        }

        address.setPerson(person);
        return this.addressRepository.save(address);
    }

    public Address findOne(UUID idPerson, Long addressId) {
        return this.personService.findOne(idPerson).getAddress()
                .stream()
                .filter((a) -> a.getId().equals(addressId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Address with id %d was not found".formatted(addressId)));
    }

    public List<Address> findAll(UUID idPerson) {
        return this.addressRepository.findAllByPersonId(idPerson);
    }

    public List<Address> findAllPageable(UUID idPerson, int page) {
        var size = 5;
        var pageable = PageRequest.of(page, size);
        var pageAddress = this.addressRepository.findAllByPersonId(idPerson, pageable);
        return pageAddress.getContent();
    }

    @Transactional
    public Address update(UUID idPerson, Long id, Address address) {
        var oldAddress = this.findOne(idPerson, id);

        var addresses = this.findAll(idPerson);

        if (mainAddressAlreadyExists(id, addresses)) {
            throw new MainAddressException("Person with id %d already have a main address".formatted(idPerson));
        }

        updateAddress(address, oldAddress);
        return this.addressRepository.save(oldAddress);
    }

    private static boolean mainAddressAlreadyExists(UUID id, List<Address> addresses) {
        return addresses.stream().anyMatch(a -> a.getMainAddress() && !a.getId().equals(id));
    }

    private static boolean mainAddressAlreadyExists(List<Address> addresses) {
        return addresses.stream().anyMatch(Address::getMainAddress);
    }

    private static void updateAddress(Address address, Address oldAddress) {
        oldAddress.setStreet(address.getStreet());
        oldAddress.setCep(address.getCep());
        oldAddress.setNumber(address.getNumber());
        oldAddress.setCity(address.getCity());
        oldAddress.setState(address.getState());
        oldAddress.setMainAddress(address.getMainAddress());
    }
}
