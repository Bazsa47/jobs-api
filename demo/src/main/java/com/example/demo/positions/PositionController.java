package com.example.demo.positions;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {

    @PostMapping(path="/positions")
    void addPosition(){

    }
}
