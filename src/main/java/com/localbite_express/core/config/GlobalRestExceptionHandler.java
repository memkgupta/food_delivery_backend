package com.localbite_express.core.config;

import com.localbite_express.core.config.Exceptions.BadRequestException;
import com.localbite_express.core.config.Exceptions.PostgresException;
import com.localbite_express.core.config.Exceptions.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity handleNotAuthorizedException(){
        return new ResponseEntity<>("You are not authorized",HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PostgresException.class)
    public ResponseEntity handlePostgresException(PostgresException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
