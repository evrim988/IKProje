package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.request.AssignShiftToUserRequestDto;
import org.example.ikproje.entity.Shift;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.UserShift;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.UserShiftRepository;
import org.example.ikproje.utility.JwtManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserShiftService {
	private final UserShiftRepository userShiftRepository;
	private final ShiftService shiftService;
	private final UserService userService;
	private final JwtManager jwtManager;
	
	public boolean assignShiftToUser(AssignShiftToUserRequestDto dto){
		Optional<Shift> optShift = shiftService.getShiftById(dto.shiftId());
		if (optShift.isEmpty()){
			throw new IKProjeException(ErrorType.SHIFT_NOT_FOUND);
		}
		Shift shift = optShift.get();
		Optional<Long> optCompanyManagerId = jwtManager.validateToken(dto.token());
		if (optCompanyManagerId.isEmpty()){
			throw new IKProjeException(ErrorType.INVALID_TOKEN);
		}
		Optional<User> optCompanyManager = userService.findById(optCompanyManagerId.get());
		Optional<User> optPersonel = userService.findById(dto.userId());
		if (optPersonel.isEmpty()||optCompanyManager.isEmpty()){
			throw new IKProjeException(ErrorType.USER_NOTFOUND);
		}
		User companyManager = optCompanyManager.get();
		User personel = optPersonel.get();
		if (companyManager.getUserRole()==EUserRole.EMPLOYEE){
			throw new IKProjeException(ErrorType.UNAUTHORIZED);
		}
		if (dto.startDate().isAfter(dto.endDate())){
			throw new IKProjeException(ErrorType.SHIFT_TIME_ERROR);
		}
		List<UserShift> personelsActiveShifts = userShiftRepository.findByUserIdAndState(personel.getId(), EState.ACTIVE);
		
		if (isDateRangeOverlapping(personelsActiveShifts, dto.startDate(), dto.endDate())){
			throw new IKProjeException(ErrorType.SHIFT_DATE_OVERLAP);
		}
		
		UserShift userShift = UserShift.builder()
										.shiftId(dto.shiftId())
										.userId(personel.getId())
										.startDate(dto.startDate())
										.endDate(dto.endDate())
		                                .build();
		userShiftRepository.save(userShift);
		
		return true;
	}
	
	
	//Burada tarihlerin çakışıp çakışmadığını kontrol ediyorum. Eğer çakışıyorlarsa ana metotta (assignShiftToUser) hata yolluyorum.
	private boolean isDateRangeOverlapping(List<UserShift> activeShifts, LocalDate startDate, LocalDate endDate){
		return activeShifts.stream().anyMatch(shift->
				(startDate.isBefore(shift.getEndDate()) || startDate.isEqual(shift.getEndDate())) &&
				(endDate.isAfter(shift.getStartDate()) || endDate.isEqual(shift.getStartDate())));
	}
}