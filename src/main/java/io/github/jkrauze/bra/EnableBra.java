package io.github.jkrauze.bra;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(value= RetentionPolicy.RUNTIME)
@Target(value= ElementType.TYPE)
@Documented
@Import(value= BraConfiguration.class)
public @interface EnableBra {
}
