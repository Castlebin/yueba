package com.xteam.ggq.model.dbrouting;

import java.lang.annotation.*;

/**
 * Created by hanxu on 2015/8/28.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBRead {
}
