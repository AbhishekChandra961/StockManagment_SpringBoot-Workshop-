package com.bridgelabz.workshop.exception;

import com.bridgelabz.workshop.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto> handleException(CustomException exception) {
        ResponseDto responseDto = new ResponseDto("Exception handling ", exception.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
