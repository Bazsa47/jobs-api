package com.example.demo.Clients;

import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Client inDB = clientRepository.findByEmail(s);
        return inDB == null;
    }
}
