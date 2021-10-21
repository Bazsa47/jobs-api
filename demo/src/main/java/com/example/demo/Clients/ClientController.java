package com.example.demo.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping(path="/clients")
    void addClient(@Valid @RequestBody Client client) throws EmailAlreadyTakenException {
        clientService.addClient(client);
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleDuplicateEmail(){

    }
}
