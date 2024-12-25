package org.example.ikproje.entity.enums;

public enum ELeaveType {

    YILLIK_IZIN(12),
    EVLILIK_IZNI(14),
    DOGUM_IZNI(60),
    UCRETSIZ_IZIN(Integer.MAX_VALUE),
    DIGER(Integer.MAX_VALUE),;
    
    private int numberOfLeaveDays;
    
    ELeaveType(int numberOfLeaveDays) {
        this.numberOfLeaveDays = numberOfLeaveDays;
    }
    
    public int getNumberOfLeaveDays() {
        return numberOfLeaveDays;
    }
}