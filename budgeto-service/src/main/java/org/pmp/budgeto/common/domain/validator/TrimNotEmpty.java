package org.pmp.budgeto.common.domain.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * Define new annotation for trim not empty String validation.
 */
@NotNull
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TrimNotEmptyValidator.class)
@Documented
public @interface TrimNotEmpty {

    /**
     * message.
     */
    String message() default "{org.pmp.budgeto.trimnotempty}";

    /**
     * validation group.
     */
    Class<?>[] groups() default {};

    /**
     * payload.
     */
    @NotNull Class<? extends Payload>[] payload() default {};

}
