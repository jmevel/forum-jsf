/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.supinfo.supforum.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@NotEmpty(message = "Password cannot be empty!")
@Size(min=6, message = "Password must have at least 6 characters!")
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPassword {

    String message() default "Invalid password, must have one upper case, one lower case and one digit!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
