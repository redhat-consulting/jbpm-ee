package org.jbpm.ee.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.slf4j.Logger;

/**
 * Disables the processing of jBPM service annotations.
 * @author bradsdavis
 *
 */
public class DisableExtension implements Extension {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(DisableExtension.class);

	<T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		Class c = pat.getAnnotatedType().getJavaClass();
		String qualifiedName = c.getName();

		if (qualifiedName.startsWith("org.jbpm.services")) {
			LOG.info("Disabling CDI: "+qualifiedName);
			pat.veto();
		}
	}

}
