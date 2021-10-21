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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
}
