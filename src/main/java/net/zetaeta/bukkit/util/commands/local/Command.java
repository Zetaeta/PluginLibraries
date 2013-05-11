package net.zetaeta.bukkit.util.commands.local;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String[] aliases();
    String value();
    String[] usage() default {};
    String[] shortUsage() default {};
    boolean useCommandArguments() default false;
    String[] boolFlags() default {};
    String[] valueFlags() default {};
    String permission();
    boolean checkPermissions() default true;
    boolean playersOnly() default false;
    /**
     * Whether the command's permission is supposed to be the parent's permission prepended to
     * the given permission.
     */
    boolean inheritPermission() default true;
}
