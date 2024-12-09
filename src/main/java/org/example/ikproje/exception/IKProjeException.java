package org.example.ikproje.exception;

import lombok.Getter;

@Getter
public class IKProjeException extends RuntimeException{
    private ErrorType errorType;
    public IKProjeException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
