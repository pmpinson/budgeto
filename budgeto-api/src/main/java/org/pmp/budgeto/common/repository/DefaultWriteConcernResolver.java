package org.pmp.budgeto.common.repository;

import com.mongodb.WriteConcern;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.WriteConcernResolver;
import org.springframework.stereotype.Component;

/**
 * Implementation of WriteConcernResolver to set db to be safe to level WriteConcern.JOURNALED
 */
@Component
public class DefaultWriteConcernResolver implements WriteConcernResolver {

    @Override
    public WriteConcern resolve(MongoAction action) {
        return WriteConcern.JOURNALED;
    }
}