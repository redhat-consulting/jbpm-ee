package org.jbpm.ee.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

/**
 * Disables the processing of jBPM service annotations.
 * @author bradsdavis
 *
 */
public class DisableExtension implements Extension {

	<T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		Class c = pat.getAnnotatedType().getJavaClass();
		String qualifiedName = c.getName();

		if (qualifiedName.startsWith("org.jbpm.services")) {
			pat.veto();
		}
	}

}
