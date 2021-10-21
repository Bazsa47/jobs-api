package com.example.demo.positions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;


@Configuration
@Profile("!test")
public class PositionConfiguration {

    @Autowired
    PositionRepository positionRepository;
    @Bean
    CommandLineRunner createUploadFolder(){
        return (args) -> {
            Position p1 = new Position(1,"Fullstack Developer","Budapest");
            Position p2 = new Position(2,"Butcher","Eger");
            Position p3 = new Position(3,"Doctor","Debrecen");
            positionRepository.saveAll(List.of(p1,p2,p3));

        };
    }
}
