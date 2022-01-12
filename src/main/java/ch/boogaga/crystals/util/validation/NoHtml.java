package ch.boogaga.crystals.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoHtmlValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoHtml {
    String message() default "Unsafe html content";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
