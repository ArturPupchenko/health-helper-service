package com.alevel.java.healthhepler.model.constraints;

import com.auth0.jwt.interfaces.Payload;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static org.hibernate.validator.constraints.CompositionType.OR;

@ConstraintComposition(OR)
@Null
@NotBlank
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = {})
public @interface NullableNotBlank {

    String message() default "{com.alevel.java.healthhelper.model.constraints.NullableNotBlank.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
