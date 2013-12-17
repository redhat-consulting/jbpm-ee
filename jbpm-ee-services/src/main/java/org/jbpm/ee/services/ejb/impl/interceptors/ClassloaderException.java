package org.jbpm.ee.services.ejb.impl.interceptors;

import java.io.IOException;

public class ClassloaderException extends IOException {

	public ClassloaderException(String message, Throwable t) {
		super(message, t);
	}
}
