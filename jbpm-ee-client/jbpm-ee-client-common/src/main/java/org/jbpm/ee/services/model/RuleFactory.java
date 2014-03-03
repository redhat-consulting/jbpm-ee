package org.jbpm.ee.services.model;

import org.jbpm.ee.services.model.rules.FactHandle;

public class RuleFactory {

	public static FactHandle convert(org.kie.api.runtime.rule.FactHandle factHandle) {
		if (factHandle == null) {
			return null;
		}
		return new FactHandle(factHandle.toExternalForm());
	}
}
