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
import org.example.ikproje.view.VwBreakSummary;
import org.example.ikproje.view.VwUserActiveShift;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserShiftService {
	private final UserShiftRepository userShiftRepository;
	private final ShiftService shiftService;
	private final UserService userService;
	private final JwtManager jwtManager;
	private final BreakService breakService;
	
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
	// Personelin vardiyasının ve molalarının detaylarını getiren metot.
	public VwUserActiveShift getActiveShiftDetailsByUserId(Long userId,String token){
		Optional<Long> optCompanyManagerId = jwtManager.validateToken(token);
		if (optCompanyManagerId.isEmpty()){
			throw new IKProjeException(ErrorType.INVALID_TOKEN);
		}
		Optional<User> optCompanyManager = userService.findById(optCompanyManagerId.get());
		if (optCompanyManager.isEmpty()){
			throw new IKProjeException(ErrorType.USER_NOTFOUND);
		}
		User companyManager = optCompanyManager.get();
		if (companyManager.getUserRole()==EUserRole.EMPLOYEE){
			throw new IKProjeException(ErrorType.UNAUTHORIZED);
		}
		
		// Burada personel'in state'i active olan shift'ini aldım.
		UserShift activeShift = userShiftRepository.findByUserIdAndState(userId, EState.ACTIVE)
		                                           .stream()
		                                           .findFirst()
		                                           .orElseThrow(() -> new IKProjeException(ErrorType.SHIFT_NOT_FOUND));
		// Burada yukarıda aldığım shift'in detaylarını (adı, başlama zamanı, bitiş zamanı) aldım.
		Shift shift = shiftService.getShiftById(activeShift.getShiftId())
		                          .orElseThrow(() -> new IKProjeException(ErrorType.SHIFT_NOT_FOUND));
		// Burada o shift'e atanmış molaları bir List içerisine aldım.
		List<VwBreakSummary> breakList = breakService.findByShiftId(shift.getId()).stream()
		                                        .map(b -> new VwBreakSummary(b.getName(), LocalTime.parse(b.getStartTime()), LocalTime.parse(b.getEndTime())))
		                                        .toList();
		// Burada da Vw'in içerisini yukarıda almış olduğum bilgilerle doldurdum.
		return new VwUserActiveShift(userId,shift.getName(),LocalTime.parse(shift.getStartTime()),
		                             LocalTime.parse(shift.getEndTime()),breakList);
	}
}