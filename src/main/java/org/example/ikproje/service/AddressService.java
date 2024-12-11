package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Address;
import org.example.ikproje.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final AddressRepository addressRepository;
	
	public void save(Address address) {
		addressRepository.save(address);
	}
	
	public void saveAll(Iterable<Address> addresses) {
		addressRepository.saveAll(addresses);
	}
	
	
}