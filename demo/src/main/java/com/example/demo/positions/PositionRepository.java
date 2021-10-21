package com.example.demo.positions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position,Long> {
    @Query(value="SELECT * FROM position where title = :title",nativeQuery = true)
    List<Position> findAllByTitle(@Param("title") String title);

    @Query(value="SELECT * FROM position where location = :location",nativeQuery = true)
    List<Position> findAllByLocation(@Param("location") String location);
}
