package github.leyan95.basic.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wujianchuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
public @interface Bundle {
    @AliasFor(value = "value", annotation = org.springframework.stereotype.Controller.class)
    String value() default "";

    /**
     * 功能点标识（多个路由绑定到一起，bundle起到分组作用）
     *
     * @return bundle id.
     */
    String id();

    @AliasFor(value = "path", annotation = RequestMapping.class)
    String[] bundlePath() default "";
}