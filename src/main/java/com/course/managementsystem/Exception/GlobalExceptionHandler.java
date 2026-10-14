package com.course.managementsystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<String> handlerCourseNotFoundException(CourseNotFoundException ex){
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());


    }
    public Map<String , String> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error->errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));
        return errors;

    }

}
