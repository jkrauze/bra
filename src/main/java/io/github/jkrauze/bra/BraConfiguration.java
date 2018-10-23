package io.github.jkrauze.bra;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "io.github.jkrauze.bra")
public class BraConfiguration {

    protected final static String ERROR_ATTRIBUTE = DefaultErrorAttributes.class.getName() + ".ERROR";
}
