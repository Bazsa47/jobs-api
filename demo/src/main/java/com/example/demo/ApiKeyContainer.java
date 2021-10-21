package com.example.demo;

import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;


public class ApiKeyContainer {

   static List<UUID> validApiKeyList;

    public static boolean contains(UUID apiKey){
        return validApiKeyList.contains(apiKey);
    }

    public static void saveApiKey(UUID apiKey){
        validApiKeyList.add(apiKey);
    }

    public static void removeApiKey(UUID apiKey){
        validApiKeyList.remove(apiKey);
    }


}
