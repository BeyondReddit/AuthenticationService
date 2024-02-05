package com.example.authserver.AOP;




import com.example.authserver.exception.InvalidCredentialsException;
import com.example.authserver.requestAndResponse.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

//    @ExceptionHandler(value = {InvalidCredentialsException.class})
//    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e){
//        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
//    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity(ErrorResponse.builder().message("Validation failed: " + ex.getMessage()).build(), HttpStatus.BAD_REQUEST);

    }
}
