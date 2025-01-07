package org.example.ikproje.view;

import org.example.ikproje.entity.enums.EUserDepartmentType;

import java.time.LocalDate;

public record VwPersonelForUpcomingBirthday(
		String firstName,
		String lastName,
		LocalDate birthDate,
		EUserDepartmentType departmentType
) {
}