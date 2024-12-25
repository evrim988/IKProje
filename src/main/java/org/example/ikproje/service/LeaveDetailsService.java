package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.LeaveDetails;
import org.example.ikproje.repository.LeaveDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveDetailsService {
	private final LeaveDetailsRepository leaveDetailsRepository;
	
	public void save (LeaveDetails leaveDetails) {
		leaveDetailsRepository.save(leaveDetails);
	}
	
	public Optional<LeaveDetails> findByLeaveId(Long leaveId) {
		return leaveDetailsRepository.findByLeaveId(leaveId);
	}
}