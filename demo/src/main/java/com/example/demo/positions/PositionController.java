package com.example.demo.positions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class PositionController {

    @Autowired
    PositionService positionService;

    @PostMapping(path="/positions")
    String addPosition(@Valid @RequestBody Position position) throws MalformedURLException {
        positionService.addPosition(position);
        return "/positions/"+position.getId();
    }
}
