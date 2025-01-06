package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.CreateNewBreakRequestDto;
import org.example.ikproje.dto.request.UpdateBreakRequestDto;
import org.example.ikproje.dto.response.BreakResponseDto;
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
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		
		LocalDateTime shiftStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(shift.getStartTime()));
		LocalDateTime shiftEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(shift.getEndTime()));
		
		// 23:00 - 07:00 vardiyası için
		if (shiftEndTime.isBefore(shiftStartTime)) {
			shiftEndTime = shiftEndTime.plusDays(1); // EndTime'ı bir gün ileri kaydır
		}
		
		LocalDateTime breakStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(dto.startTime()));
		LocalDateTime breakEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(dto.endTime()));
		
		// Mola başlangıç saati vardiya başlangıç saatinden önce ise düzelt
		if (breakStartTime.isBefore(shiftStartTime)) {
			breakStartTime = shiftStartTime.plusMinutes(1); // 1 dakika ileri alarak düzelt
			
		}
		
		
		if (breakStartTime.isAfter(shiftEndTime) || breakEndTime.isAfter(shiftEndTime)) {
			throw new IKProjeException(ErrorType.BREAK_TIME_ERROR);
		}
		
		Break newBreak = BreakMapper.INSTANCE.fromCreateNewBreakDto(dto);
		breakRepository.save(newBreak);
		return true;
	}
	
	private User userControl(String token) {
		Long userId = jwtManager.validateToken(token).orElseThrow(() -> new IKProjeException(ErrorType.INVALID_TOKEN));
		User user = userService.findById(userId).orElseThrow(() -> new IKProjeException(ErrorType.USER_NOTFOUND));
		if (user.getUserRole() == EUserRole.EMPLOYEE) {
			throw new IKProjeException(ErrorType.UNAUTHORIZED);
		}
		return user;
	}
	
	public List<Break> findByShiftId(Long shiftId) {
		return breakRepository.findByShiftIdOrderById(shiftId);
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
		return breakRepository.findByShiftIdOrderById(shiftId);
	}

	public List<BreakResponseDto> getAllBreak(String token) {
		User user = userControl(token);
		Long companyId = user.getCompanyId();
		List<Break> breakList = breakRepository.findAllByStateOrderById(EState.ACTIVE);
		List<BreakResponseDto> dtoList = new ArrayList<>();

		for (Break item: breakList) {
			Optional<Shift> optionalShift = shiftService.findById(item.getShiftId());
			if(optionalShift.isEmpty()){
				throw new IKProjeException(ErrorType.SHIFT_NOT_FOUND);
			}

			// Kullanıcının şirketiyle eşleşmeyen molaları atla
			if(!optionalShift.get().getCompanyId().equals(companyId)){
				continue;
			}

			BreakResponseDto dto = BreakResponseDto.builder()
					.id(item.getId())
					.name(item.getName())
					.shiftName(optionalShift.get().getName())
					.startTime(item.getStartTime())
					.endTime(item.getEndTime())
					.build();

			dtoList.add(dto);
		}
		return dtoList;
	}
}