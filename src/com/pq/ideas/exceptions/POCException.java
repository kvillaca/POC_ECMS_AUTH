package com.pq.ideas.exceptions;

/**
 * POC custom exception, to be used instead the regular Java exceptions.
 * Everytime a JVM Exception need to be handled, we throw if needed this custom exception
 * as we can control and map the exceptions and warnings via only one exception that we 
 * can later on map to a Java POJO in order to have just one catch in the REST layer.
 * 
 * I didn't have time so far to finalize the message parser to the REST interface. 
 *   
 * @author kvillaca
 *
 */
public class POCException extends Exception {

	private static final long serialVersionUID = 4698902858085449990L;
	
	public POCException() {
		super();
	}
	
	
	public POCException(final String message) {
		super(message);
	}
	
	public POCException(final Throwable cause) {
		super(cause);
	}
	
	
	public POCException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	
	public POCException(final String message, final Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
		super(message, cause, enableSuppresion, writableStackTrace);
	}

}
