package com.example.demo;

import com.example.demo.Clients.Client;
import com.example.demo.Clients.ClientRepository;
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
public class ClientControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ClientRepository clientRepository;

    String path = "/clients";


    public <T> ResponseEntity<T> postClients(Object request, Class<T> response){
        return testRestTemplate.postForEntity(path,request,response);
    }

    Client createValidClient(){
        return new Client(1,"client1","client1@gmail.com");
    }
    @Before
    public void cleanup(){
        clientRepository.deleteAll();
    }

    @Test
    public void postClient_isValid_receiveOk() {
        Client client = createValidClient();
        ResponseEntity<Object> response = postClients(client, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUser_whenClientIsValid_userSavedToDatabase() {
        Client client = createValidClient();
        postClients(client,Object.class);
        assertThat(clientRepository.count()).isEqualTo(1);
    }

    @Test
    public void postUser_whenClientHasInvalidName_receiveBadRequest() {
        Client client = createValidClient();
        String charOf101Characters = IntStream.rangeClosed(1,101).mapToObj(x -> "a").collect(Collectors.joining());
        client.setName(charOf101Characters);
        ResponseEntity<Object> response = postClients(client, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenClientHasInvalidEmail_receiveBadRequest() {
        Client client = createValidClient();
        String invalidEmail = "invalidemail";
        client.setEmail(invalidEmail);
        ResponseEntity<Object> response = postClients(client, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenClientHasDuplicateEmail_receiveBadRequest() {
        Client client = createValidClient();
        postClients(client, Object.class);

        Client client2 = createValidClient();
        client.setId(2);
        ResponseEntity<Object> response = postClients(client, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
