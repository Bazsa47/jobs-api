package com.example.demo.positions.vm;

import com.example.demo.positions.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class PositionVM {

    String title;

    String location;

    String url;

    public PositionVM(Position position) {
            this.title = position.getTitle();
            this.location = position.getLocation();
            this.url = "/positions/"+position.getId();
    }
}
