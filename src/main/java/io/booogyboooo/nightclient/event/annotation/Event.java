package io.booogyboooo.nightclient.event.annotation;

import java.lang.annotation.*;
import io.booogyboooo.nightclient.event.EventType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event {
    EventType eventType();

    boolean exclusive() default false;

    int priority() default 1;
}