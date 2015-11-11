package org.pmp.budgeto.common.domain;

import com.mongodb.DuplicateKeyException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.pmp.budgeto.common.tools.ValidatorTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * implementation of the domain common
 */
@Component
public class DomainToolsImpl implements DomainTools {

    private final TranslatorTools translatorTools;

    private final ValidatorTools validatorTools;

    @Autowired
    public DomainToolsImpl(TranslatorTools translatorTools, ValidatorTools validatorTools) {
        this.translatorTools = Validate.notNull(translatorTools);
        this.validatorTools = Validate.notNull(validatorTools);
    }

    @Override
    public void validate(Object object, String objectDesc) throws DomainException {
        Validate.notNull(object, translatorTools.get("object.null", translatorTools.get(objectDesc)));

        DomainValidationError[] violations = toValidationErrorArray(validatorTools.validate(object));
        if (violations.length != 0) {
            throw new DomainValidationException(translatorTools.get("object.not.valid", translatorTools.get(objectDesc)), violations);
        }
    }

    private <T> DomainValidationError[] toValidationErrorArray(Set<ConstraintViolation<T>> constraintViolations) {
        Map<String, DomainValidationError> result = new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String key = DomainValidationError.FIELD_ALL;
            if (StringUtils.isNotBlank(constraintViolation.getPropertyPath().toString())) {
                key = constraintViolation.getPropertyPath().toString();
            }
            String[] errors = new String[]{};
            if (result.containsKey(key)) {
                errors = result.get(key).getErrors();
            }
            result.put(key, new DomainValidationError(key, ArrayUtils.add(errors, constraintViolation.getMessage())));
        }

        return result.values().toArray(new DomainValidationError[]{});
    }

    @Override
    public boolean isConstraintViolationExceptionFor(DataAccessException parentException, String constraintName) {
        boolean res = true;
        if (parentException == null || parentException.getCause() == null || constraintName == null) {
            res = false;
        } else if (!(parentException.getCause() instanceof DuplicateKeyException) || !parentException.getMessage().contains(constraintName)) {
            res = false;
        }
        return res;
    }

}
