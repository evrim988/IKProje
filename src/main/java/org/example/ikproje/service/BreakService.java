package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.CreateNewBreakRequestDto;
import org.example.ikproje.dto.request.UpdateBreakRequestDto;
import org.example.ikproje.entity.Break;
import org.example.ikproje.entity.Shift;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.mapper.BreakMapper;
import org.example.ikproje.repository.BreakRepository;
import org.example.ikproje.utility.JwtManager;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BreakService {
	private final BreakRepository breakRepository;
	private final JwtManager jwtManager;
	private final ShiftService shiftService;
	private final UserService userService;
	
	public Boolean createNewBreak(CreateNewBreakRequestDto dto) {
		userControl(dto.token());
		Shift shift = shiftService.getShiftById(dto.shiftId())
		                          .orElseThrow(() -> new IKProjeException(ErrorType.SHIFT_NOT_FOUND));
		LocalTime shiftStartTime = LocalTime.parse(shift.getStartTime());
		LocalTime shiftEndTime = LocalTime.parse(shift.getEndTime());
		LocalTime breakStartTime = LocalTime.parse(dto.startTime());
		LocalTime breakEndTime = LocalTime.parse(dto.endTime());
		if (breakStartTime.isBefore(shiftStartTime) || breakEndTime.isAfter(shiftEndTime)) {
			throw new IKProjeException(ErrorType.BREAK_TIME_ERROR);
		}
		Break newBreak = BreakMapper.INSTANCE.fromCreateNewBreakDto(dto);
		breakRepository.save(newBreak);
		return true;
	}
	
	private void userControl(String token) {
		Long userId = jwtManager.validateToken(token).orElseThrow(() -> new IKProjeException(ErrorType.INVALID_TOKEN));
		User user = userService.findById(userId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
		if (user.getUserRole() == EUserRole.EMPLOYEE) {
			throw new IKProjeException(ErrorType.UNAUTHORIZED);
		}
	}
	
	public List<Break> findByShiftId(Long shiftId) {
		return breakRepository.findByShiftId(shiftId);
	}
	
	public Boolean updateBreak(UpdateBreakRequestDto dto) {
		userControl(dto.token());
		Shift shift = shiftService.getShiftById(dto.shiftId())
		                         .orElseThrow(() -> new IKProjeException(ErrorType.SHIFT_NOT_FOUND));
		// break kelimesi java'da keyword olduğu için aBreak olarak tanımladım.
		Break aBreak = breakRepository.findById(dto.breakId())
		                              .orElseThrow(() -> new IKProjeException(ErrorType.BREAK_NOT_FOUND));
		aBreak.setName(dto.name());
		aBreak.setStartTime(dto.startTime());
		aBreak.setEndTime(dto.endTime());
		aBreak.setShiftId(dto.shiftId());
		breakRepository.save(aBreak);
		return true;
	}
	
	public Boolean deleteBreak(String token, Long breakId) {
		userControl(token);
		Break aBreak =
				breakRepository.findById(breakId).orElseThrow(() -> new IKProjeException(ErrorType.BREAK_NOT_FOUND));
		aBreak.setState(EState.PASSIVE);
		breakRepository.save(aBreak);
		return true;
	}
	
	public List<Break> getBreaksByShiftId(String token, Long shiftId) {
		userControl(token);
		shiftService.getShiftById(shiftId).orElseThrow(() -> new IKProjeException(ErrorType.SHIFT_NOT_FOUND));
		return breakRepository.findByShiftId(shiftId);
	}
}