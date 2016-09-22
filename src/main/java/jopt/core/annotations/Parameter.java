package jopt.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jopt.core.Algorithm;

/**
 * This annotation is used to annotate attributes in algorithm classes. This 
 * annotation avoids to implement {@link Algorithm#doSetParameter(java.lang.String, java.lang.Object) 
 * Algorithm.doSetParameter()} method to handle parameters setting.
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Parameter {
    
    /**
     * The name of the parameter. If not defined, the field name is used as 
     * parameter name.
     */
    String value() default "";
    
}
