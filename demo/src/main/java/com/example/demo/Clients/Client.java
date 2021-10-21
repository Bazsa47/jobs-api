package com.example.demo.Clients;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue
    long id;

    @Size(max = 100)
    String name;

    String email;
}
