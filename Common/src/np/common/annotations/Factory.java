package np.common.annotations;

import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;

@Retention(CLASS)
public @interface Factory {
	Class<?> type();
}
