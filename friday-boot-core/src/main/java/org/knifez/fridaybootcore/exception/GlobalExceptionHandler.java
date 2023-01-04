package org.knifez.fridaybootcore.exception;

import org.knifez.fridaybootcore.dto.FridayResult;
import org.knifez.fridaybootcore.enums.ResultStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 处理验证参数异常
     *
     * @param ex ex
     * @return {@link ResponseEntity}<{@link FridayResult}<{@link Map}<{@link String}, {@link String}>>>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FridayResult<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.OK).body(FridayResult.fail(ResultStatus.CHECK_PARAMS_FAILED, errors));
    }

    /**
     * 一般异常处理
     *
     * @param e e
     * @return {@link ResponseEntity}<{@link FridayResult}<{@link Void}>>
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<FridayResult<Void>> exceptionHandler(Exception e) {
        FridayResult<Void> errorResponse;
        if (e instanceof FridayResultException exp) {
            errorResponse = FridayResult.fail(exp.getResultStatus());
        } else {
            errorResponse = FridayResult.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        // 拦截所有错误码并返回200
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}