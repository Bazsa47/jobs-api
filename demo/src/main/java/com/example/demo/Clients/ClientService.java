package com.example.demo.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public void addClient(Client client) throws EmailAlreadyTakenException {
        Client inDB = clientRepository.findByEmail(client.getEmail());
        if(inDB != null) throw new EmailAlreadyTakenException("Email already taken");
        clientRepository.save(client);
    }
}
