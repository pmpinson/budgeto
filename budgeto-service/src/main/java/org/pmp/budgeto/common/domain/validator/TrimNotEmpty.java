package org.pmp.budgeto.common.domain.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

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
    @NotNull
    Class<? extends Payload>[] payload() default {};

}
