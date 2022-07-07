package com.knifez.fridayboot.exception;


import com.knifez.fridayboot.domain.dto.FridayResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author zhang
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<FridayResponse<Void>> exceptionHandler(Exception e) {
        FridayResponse<Void> errorResponse;
        if (e instanceof ResponseStatusException) {
            errorResponse=FridayResponse.fail(((ResponseStatusException) e).getRawStatusCode(),((ResponseStatusException) e).getReason());
        }else {
            errorResponse=FridayResponse.fail(500,e.getLocalizedMessage());
        }
        // 拦截所有错误码并返回200
        return ResponseEntity.status(200).body(errorResponse);
    }
}