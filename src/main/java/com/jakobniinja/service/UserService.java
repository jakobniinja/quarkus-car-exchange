package com.jakobniinja.service;

import com.jakobniinja.dtos.CreateUserDto;
import com.jakobniinja.utils.Counter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

@ApplicationScoped
public class UserService {

    Counter counter = new Counter();

    @Inject
    MongoClient mongoClient;

    public List<CreateUserDto> find(String email) {
        List<CreateUserDto> list = new ArrayList<>();

        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();


                CreateUserDto userDto = new CreateUserDto();
                userDto.set_id(document.getString("_id"));
                userDto.setEmail(document.getString("email"));
                userDto.setPassword(document.getString("password"));

                if (email.equalsIgnoreCase(userDto.getEmail())) {
                    list.add(userDto);
                }
            }
        }
        return list;
    }


    public void create(String email, String password) {

        counter.increment();

        Document document = new Document()
                .append("_id", String.valueOf(counter.getVal()))
                .append("email", email)
                .append("password", password);

        getCollection().insertOne(document);
    }


    public CreateUserDto findOne(String _id) {

        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = new Document();

                if (_id.equalsIgnoreCase(document.getString("_id"))) {
                    CreateUserDto userDto = new CreateUserDto();
                    userDto.set_id(document.getString("_id"));
                    userDto.setEmail(document.getString("email"));
                    userDto.setPassword(document.getString("password"));
                    return userDto;
                }
            }
        }
        return null;
    }

    public List<CreateUserDto> find() {
        List<CreateUserDto> list = new ArrayList<>();

        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = new Document();
                CreateUserDto userDto = new CreateUserDto();
                userDto.set_id(document.getString("_id"));
                userDto.setEmail(document.getString("email"));
                userDto.setPassword(document.getString("password"));

                list.add(userDto);
            }

        }
        return list;

    }

    public void update() {

    }

    public void remove() {

    }


    public void deleteAll() {

        Bson query = gt("_id", "0");
        DeleteResult result = getCollection().deleteMany(query);
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("user").getCollection("user");
    }
}
