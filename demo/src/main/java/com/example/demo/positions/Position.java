package com.example.demo.positions;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Data
@Entity
public class Position {
    @Id
    @GeneratedValue
    long id;

    @Size(max = 50)
    String title;

    @Size(max = 50)
    String location;

    public Position(long id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }
}
