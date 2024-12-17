package org.example.ikproje.entity.enums;

import java.time.LocalDate;

public enum EMembershipType {
	ONE_MONTH(1,LocalDate.now(),LocalDate.now().plusMonths(1)),
	THREE_MONTHS(0.95,LocalDate.now(),LocalDate.now().plusMonths(3)),
	SIX_MONTHS(0.85,LocalDate.now(),LocalDate.now().plusMonths(6)),
	TWELVE_MONTHS(0.75,LocalDate.now(),LocalDate.now().plusMonths(12)),;
	
	private double discountRate;
	private LocalDate startDate;
	private LocalDate endDate;
	
	EMembershipType(double discountRate, LocalDate startDate, LocalDate endDate) {
		this.discountRate = discountRate;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
}