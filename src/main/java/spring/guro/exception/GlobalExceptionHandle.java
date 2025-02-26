package spring.guro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import spring.guro.dto.newapi.resp.ErrorResp;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    // 값 검증 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResp> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity
            .status(ErrorEnum.VALIDATION_ERROR.code)
            .body(new ErrorResp(
                ErrorEnum.VALIDATION_ERROR.code,
                ErrorEnum.VALIDATION_ERROR.status,
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()
            ));
    }

    // 로그인 이메일이 존재하지 않는 경우 예외 처리
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResp> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
            .status(ErrorEnum.LOGIN_EMAIL_INVALID.code)
            .body(new ErrorResp(
                ErrorEnum.LOGIN_EMAIL_INVALID.code,
                ErrorEnum.LOGIN_EMAIL_INVALID.status,
                ex.getMessage()
            ));
    }

    // 로그인 패스워드 불일치 예외 처리
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResp> handleIllegalArgumentException(BadCredentialsException ex) {
        return ResponseEntity
            .status(ErrorEnum.LOGIN_PASSWORD_UNMATCHED.code)
            .body(new ErrorResp(
                ErrorEnum.LOGIN_PASSWORD_UNMATCHED.code,
                ErrorEnum.LOGIN_PASSWORD_UNMATCHED.status,
                ex.getMessage()
            ));
    }

    // customexception 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResp> handleCustomException(CustomException ex) {
        ex.printStackTrace();
        String errMessage = ex.getMessage().equals("") ? ex.getErrorEnum().message : ex.getMessage();
        ErrorResp errorResp = new ErrorResp(ex.getErrorEnum().code, ex.getErrorEnum().status, errMessage);
        return ResponseEntity
            .status(ex.getErrorEnum().code)
            .body(errorResp);
    }

    // 다른 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResp> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResp(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage()
            ));
    }
}
