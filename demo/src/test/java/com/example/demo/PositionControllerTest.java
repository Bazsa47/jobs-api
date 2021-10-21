package com.example.demo;

import com.example.demo.Clients.Client;
import com.example.demo.positions.Position;
import com.example.demo.positions.PositionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PositionControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    
    @Autowired
    PositionRepository positionRepository;

    String path = "/positions";
    public <T> ResponseEntity<T> postPositions(Object request, Class<T> response){
        return testRestTemplate.postForEntity(path,request,response);
    }

    Position createValidPosition(){
        return new Position(1,"Fullstack developer","Budapest");
    }

    public <T> ResponseEntity<T> getPositions(String path, ParameterizedTypeReference<T> responseType){
        return testRestTemplate.exchange(path, HttpMethod.GET,null,responseType);
    }

    @Before
    public void cleanup(){
        positionRepository.deleteAll();
    }

    @Test
    public void postPosition_IsValid_ReceiveOk() {
        Position position = createValidPosition();
        ResponseEntity<String> response = postPositions(position, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postPosition_whenPositionIsValid_positionSavedToDatabase() {
        Position position = createValidPosition();
        postPositions(position,String.class);
        assertThat(positionRepository.count()).isEqualTo(1);
    }

    @Test
    public void postPosition_withInvalidTitle_ReceiveBadRequest() {
        Position position = createValidPosition();
        String charOf51Characters = IntStream.rangeClosed(1,101).mapToObj(x -> "a").collect(Collectors.joining());
        position.setTitle(charOf51Characters);
        ResponseEntity<Object> response = postPositions(position, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postPosition_withInvalidLocation_ReceiveBadRequest() {
        Position position = createValidPosition();
        String charOf51Characters = IntStream.rangeClosed(1,101).mapToObj(x -> "a").collect(Collectors.joining());
        position.setLocation(charOf51Characters);
        ResponseEntity<Object> response = postPositions(position, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postPosition_isValid_weReceiveURLWithPositionTitle() {
        Position position = createValidPosition();
        ResponseEntity<String> response = postPositions(position, String.class);
        assertThat(response.getBody().contains("/positions/"+position.getId())).isTrue();
     }

    @Test
    public void getPositions_whenThereAreNoPositionsInDatabase_weReceiveOk() {
        ResponseEntity<Object> forEntity = getPositions(path,new ParameterizedTypeReference<Object>(){});
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getPositions_whenThereIsAPositionsInDb_receivePosition() {
        Position p = createValidPosition();
        positionRepository.save(p);
        ResponseEntity<List<Position>> response = getPositions(path,new ParameterizedTypeReference<List<Position>>(){});
        assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test
    public void getPositions_whenThereAreMultiplePositionsInDb_receiveAllPositions() {
        Position p1 = createValidPosition();
        positionRepository.save(p1);
        Position p2 = createValidPosition();
        p2.setId(2);
        positionRepository.save(p2);
        Position p3 = createValidPosition();
        p3.setId(3);
        positionRepository.save(p3);
        Position p4 = createValidPosition();
        p4.setId(4);
        positionRepository.save(p4);
        ResponseEntity<List<Position>> response = getPositions(path,new ParameterizedTypeReference<List<Position>>(){});
        assertThat(response.getBody().size()).isEqualTo(4);
    }

    @Test
    public void getPosition_whenPositionIdExists_receiveOk() {
        ResponseEntity<Object> forEntity = getPositions(path+"/1",new ParameterizedTypeReference<Object>(){});
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
