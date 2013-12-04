#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class TestRuntimeException extends RuntimeException {
	
	public TestRuntimeException(String message) {
		super(message);
	}
}
