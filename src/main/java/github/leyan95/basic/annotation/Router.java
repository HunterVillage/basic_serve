package github.leyan95.basic.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wujianchuan 2019/2/20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface Router {

    @AliasFor(value = "value", annotation = RequestMapping.class)
    String[] id();

    String name();

    String description() default "";

    /**
     * You must have one of the auth to request for this router.
     * <p>
     * You can visit this router without any auth only when the auth array is empty.
     *
     * @return auth array
     */
    String[] auth() default {};

    @AliasFor(value = "method", annotation = RequestMapping.class)
    RequestMethod[] method() default {RequestMethod.GET};

    @AliasFor(value = "produces", annotation = RequestMapping.class)
    String[] produces() default {"application/json; charset=UTF-8"};
}
