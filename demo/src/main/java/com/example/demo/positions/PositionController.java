package com.example.demo.positions;

import com.example.demo.positions.vm.PositionVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

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
    List<PositionVM> getPositions(){
        return positionService.getPositions();
    }

    @GetMapping(path="/{id}")
    Optional<Position> getPosition(@PathVariable Long id){
        return positionService.getPosition(id);
    }

    @GetMapping(path="/search")
    List<PositionVM> getPositionsWithSearchData(@RequestParam(required = false)String location, @RequestParam(required = false) String title){
       return positionService.getPositionsWithSearchData(location,title);
    }

}
