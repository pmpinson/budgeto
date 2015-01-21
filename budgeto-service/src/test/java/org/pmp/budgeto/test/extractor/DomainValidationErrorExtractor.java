package org.pmp.budgeto.test.extractor;

import com.google.common.collect.Lists;
import org.assertj.core.api.iterable.Extractor;
import org.assertj.core.groups.Tuple;
import org.pmp.budgeto.common.domain.DomainValidationError;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Johanna on 18/01/2015.
 */
public class DomainValidationErrorExtractor implements Extractor<DomainValidationError, Tuple> {

    @Override
    public Tuple extract(org.pmp.budgeto.common.domain.DomainValidationError o) {
        List<String> lst = Lists.newArrayList(o.getErrors());
        Collections.sort(lst);
        return Tuple.tuple(o.getField(), Arrays.toString(lst.toArray()));
    }
}
