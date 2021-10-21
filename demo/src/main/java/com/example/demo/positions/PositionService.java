package com.example.demo.positions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    PositionRepository positionRepository;

    public void addPosition(Position position) {
        positionRepository.save(position);
    }
}
