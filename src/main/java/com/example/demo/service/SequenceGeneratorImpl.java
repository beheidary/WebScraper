package com.example.demo.service;

import com.example.demo.entity.MongoSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@RequiredArgsConstructor
public class SequenceGeneratorImpl implements SequenceGenerator{

    private final MongoTemplate mongoTemplate;

    @Override
    public Long generateNextSequence(String name) {

        MongoSequence counter = mongoTemplate.findAndModify(query(where("_id").is(name)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                MongoSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }
}
