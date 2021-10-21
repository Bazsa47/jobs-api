package com.example.demo.positions;

import com.example.demo.positions.vm.PositionVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    PositionRepository positionRepository;

    public void addPosition(Position position) {
        positionRepository.save(position);
    }

    public List<PositionVM> getPositions() {
        return convertIntoVM(positionRepository.findAll());
    }

    public Optional<Position> getPosition(Long id) {
        return positionRepository.findById(id);
    }

    public List<PositionVM> getPositionsWithSearchData(String location, String title) {
        if (location == null && title != null){
            return convertIntoVM(positionRepository.findAllByTitle(title));
        }
        if(title== null && location != null){
            return convertIntoVM(positionRepository.findAllByLocation(location));
        }
        if (title == null && location == null) return convertIntoVM(positionRepository.findAll());

        List<Position> listOfTitle;
        List<Position> listOfLocation;

        listOfLocation = positionRepository.findAllByLocation(location);
        listOfTitle = positionRepository.findAllByTitle(title);

        listOfLocation.addAll(listOfTitle);
        return convertIntoVM(listOfLocation);
    }


    List<PositionVM> convertIntoVM(List<Position> positions){
        List<PositionVM> result = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            result.add(new PositionVM(positions.get(i)));
        }

        return result;
    }
}
