package com.anurag.journalapp.repository;

import com.anurag.journalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImp {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> getUsersForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
