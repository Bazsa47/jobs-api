package com.example.demo.positions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping(path = "/positions")
public class PositionController {

    @Autowired
    PositionService positionService;

    @PostMapping
    String addPosition(@Valid @RequestBody Position position) throws MalformedURLException {
        positionService.addPosition(position);
        return "/positions/"+position.getId();
    }

    @GetMapping
    List<Position> getPositions(){
        return positionService.getPositions();
    }

    @GetMapping(path="/{id}")
    void getPosition(@PathVariable Long id){

    }
}
