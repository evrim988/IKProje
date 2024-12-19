package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.UserDetails;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.UserDetailsRepository;
import org.example.ikproje.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
	private final UserDetailsRepository userDetailsRepository;
	
	public void save(UserDetails userDetails) {
		userDetailsRepository.save(userDetails);
	}

	public UserDetails findByUserId(Long id) {
		return userDetailsRepository.findByUserId(id).orElseThrow(()->new IKProjeException(ErrorType.PAGE_NOT_FOUND));
	}
}