package com.jakobniinja.service;

import com.jakobniinja.dtos.User;
import com.jakobniinja.utils.Counter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

@ApplicationScoped
public class UserService {

    @Inject
    MongoClient mongoClient;

    public List<User> find() {
        List<User> list = new ArrayList<>();

        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                User userDto = new User();
                userDto.set_id(document.getString("_id"));
                userDto.setEmail(document.getString("email"));
                userDto.setPassword(document.getString("password"));
                list.add(userDto);
            }
        }
        return list;
    }

    public List<User> find(String email) {
        List<User> list = new ArrayList<>();

        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                User userDto = new User();
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

    public User findOne(String _id) {

        List<User> users = find();

        for (User u : users) {
            if (_id.equalsIgnoreCase(u.get_id())) {
                return u;
            }

        }
        return null;
    }

    public void create(User user) {


        Document document = new Document()
                .append("_id", user.get_id())
                .append("email", user.getEmail())
                .append("password", user.getPassword());

        getCollection().insertOne(document);
    }


    public User update(User userDto) throws Exception {
        User user = findOne(userDto.get_id());

        if (user == null) {
            throw new Exception("user not found!");
        }

        Optional.ofNullable(userDto.get_id()).ifPresent(user::set_id);
        Optional.ofNullable(userDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userDto.getPassword()).ifPresent(user::setPassword);

        return user;

    }

    public void remove(String _id) throws Exception {
        User user = findOne(_id);

        if (user == null) {
            throw new Exception("user does not exist!");
        }

        Bson query = eq("_id", _id);
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
