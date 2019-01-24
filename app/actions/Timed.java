package actions;

import java.lang.annotation.*;

import play.mvc.With;

@With(TimerAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timed { }