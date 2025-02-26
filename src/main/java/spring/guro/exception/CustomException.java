package spring.guro.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorEnum errorEnum;

    public CustomException(ErrorEnum errorEnum) {
        super("");
        this.errorEnum = errorEnum;
    }

    public CustomException(ErrorEnum errorEnum, String message) {
        super(message);
        this.errorEnum = errorEnum;
    }
}
