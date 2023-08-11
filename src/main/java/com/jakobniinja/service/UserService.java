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
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

@ApplicationScoped
public class UserService {

    Counter counter = new Counter();

    @Inject
    MongoClient mongoClient;

    public List<CreateUserDto> list() {
        List<CreateUserDto> list = new ArrayList<>();

        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                CreateUserDto createUserDto = new CreateUserDto();
                createUserDto.set_id(document.getString("_id"));
                createUserDto.setEmail(document.getString("email"));
                createUserDto.setPassword(document.getString("password"));
                list.add(createUserDto);
            }
        }
        return list;
    }


    public void add(CreateUserDto createUserDto) {

        counter.increment();

        Document document = new Document()
                .append("_id", createUserDto.get_id())
                .append("email", createUserDto.getEmail())
                .append("password", createUserDto.getPassword());

        getCollection().insertOne(document);
    }


    public void delete(String id) {

        Bson query = eq("_id", id);
        DeleteResult result = getCollection().deleteOne(query);

    }

    public void deleteAll() {

        Bson query = gt("_id", "0");
        DeleteResult result = getCollection().deleteMany(query);
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("user").getCollection("user");
    }
}
