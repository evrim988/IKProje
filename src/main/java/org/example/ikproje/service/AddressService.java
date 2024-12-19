package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Address;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
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


	public Address findById(Long addressId) {
		return addressRepository.findById(addressId).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
	}
}