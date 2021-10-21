package com.example.demo.Clients;

import com.example.demo.ApiKey;
import com.example.demo.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping(path="/clients")
    ApiKey addClient(@Valid @RequestBody Client client) throws EmailAlreadyTakenException {
        clientService.addClient(client);
        return new ApiKey();
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleDuplicateEmail(){

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        ApiError apiError = new ApiError(400,"Validation error", request.getServletPath());
        BindingResult result = exception.getBindingResult();

        Map<String,String> validationErrors = new HashMap<>();

        for(FieldError fieldError : result.getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        apiError.setValidationErrors(validationErrors);
        return apiError;
    }
}
