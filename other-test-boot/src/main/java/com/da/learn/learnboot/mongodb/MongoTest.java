package com.da.learn.learnboot.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoTest {

    public static void main(String[] args) {

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://da:1qaz!QAZ@cluster0-hombj.mongodb.net/test?retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");
        String name = database.getName();
        System.out.println(name);
    }
}
