package com.sdewa.order.service;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.sdewa.order.entity.SequenceEntity;

import lombok.RequiredArgsConstructor;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class SequenceGeneratorServices {

    private final MongoOperations mongoOperations;

    public long generateNextOrderId() {

        SequenceEntity counter = mongoOperations.findAndModify(
                query(where("_id").is("sequence")),
                new Update().inc("sequence", 1),
                options().returnNew(true).upsert(true),
                SequenceEntity.class
            );

        return counter.getSequence();
    }

}
