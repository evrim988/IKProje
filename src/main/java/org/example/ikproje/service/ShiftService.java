package org.example.ikproje.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.CreateNewShiftRequestDto;
import org.example.ikproje.dto.request.UpdateShiftRequestDto;
import org.example.ikproje.entity.Shift;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.mapper.ShiftMapper;
import org.example.ikproje.repository.ShiftRepository;
import org.example.ikproje.utility.JwtManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShiftService {
	private final ShiftRepository shiftRepository;
	private final UserService userService;
	private final JwtManager jwtManager;
	
	public Boolean createNewShift(CreateNewShiftRequestDto dto){
		User user = getUser(dto.token());
		Shift shift = ShiftMapper.INSTANCE.fromCreateNewShiftDto(dto);
		shift.setCompanyId(user.getCompanyId());
		shiftRepository.save(shift);
		return true;
	}
	
	private User getUser(String token) {
		Long userId = jwtManager.validateToken(token).orElseThrow(() -> new IKProjeException(ErrorType.INVALID_TOKEN));
		User user = userService.findById(userId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
		if (user.getUserRole() == EUserRole.EMPLOYEE) {
			throw new IKProjeException(ErrorType.UNAUTHORIZED);
		}
		return user;
	}
	
	public Optional<Shift> getShiftById(Long shiftId){
		return shiftRepository.findById(shiftId);
	}
	
	public Boolean updateShift(UpdateShiftRequestDto dto) {
		User user = getUser(dto.token());
		Shift shift = getShiftById(dto.shiftId()).orElseThrow(() -> new IKProjeException(ErrorType.SHIFT_NOT_FOUND));
		shift.setCompanyId(user.getCompanyId());
		shift.setStartTime(dto.startTime());
		shift.setEndTime(dto.endTime());
		shift.setName(dto.name());
		shiftRepository.save(shift);
		return true;
	}
	
	public Boolean deleteShift(String token, Long shiftId) {
		getUser(token);
		Shift shift = getShiftById(shiftId).orElseThrow(() -> new IKProjeException(ErrorType.SHIFT_NOT_FOUND));
		shift.setState(EState.PASSIVE);
		shiftRepository.save(shift);
		return true;
	}
	
	public List<Shift> getAllShiftsByCompanyId(String token) {
		User user = getUser(token);
		return shiftRepository.getAllByCompanyIdAndStateOrderById(user.getCompanyId(),EState.ACTIVE);
	}

	public Long findShiftCountByCompanyId(Long companyId){
		return shiftRepository.countByCompanyId(companyId);
	}

	public Optional<Shift> findById(Long shiftId) {
		return shiftRepository.findById(shiftId);
	}
	
	
}