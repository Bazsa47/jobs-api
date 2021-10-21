package com.example.demo.Clients;

public class EmailAlreadyTakenException extends Throwable {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
