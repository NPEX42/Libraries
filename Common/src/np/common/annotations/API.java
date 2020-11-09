package np.common.annotations;

import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
@Retention(CLASS)
@Documented
public @interface API {
	public Level level() default Level.UNSTABLE; 
	
	public static enum Level {
		STABLE, UNSTABLE, IN_TESTING
	}
}
