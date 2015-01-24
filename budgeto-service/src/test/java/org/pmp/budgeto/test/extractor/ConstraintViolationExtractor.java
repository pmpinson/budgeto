package org.pmp.budgeto.test.extractor;

import org.assertj.core.api.iterable.Extractor;
import org.assertj.core.groups.Tuple;

import javax.validation.ConstraintViolation;

/**
 * Created by Johanna on 18/01/2015.
 */
public class ConstraintViolationExtractor implements Extractor<ConstraintViolation, Tuple> {
    @Override
    public Tuple extract(ConstraintViolation o) {
        return Tuple.tuple(o.getPropertyPath().toString(), o.getMessage());
    }
};
