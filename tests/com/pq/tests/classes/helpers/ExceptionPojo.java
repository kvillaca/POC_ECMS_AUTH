package com.pq.tests.classes.helpers;

import java.io.Serializable;
import java.lang.reflect.Method;

public class ExceptionPojo implements Serializable {

	private static final long serialVersionUID = -1994112616869950788L;

	private static final String STATUS_CODE = "getStatusCode";
	private static final String MESSAGE = "getMessage";
	private static final String CAUSE = "getCause";
	private static final String EXCEPTION = "getException";

	private String statusCode;
	private String message;
	private String cause;
	private String exception;


	public ExceptionPojo() {
		super();
	}


	public ExceptionPojo(String statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}


	public ExceptionPojo(String statusCode, String message, String cause, String exception) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.cause = cause;
		this.exception = exception;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getCause() {
		return cause;
	}


	public void setCause(String cause) {
		this.cause = cause;
	}


	public String getException() {
		return exception;
	}


	public void setException(String exception) {
		this.exception = exception;
	}


	public void setValues(final String className, final Object anObjectWithSimilarStructure) throws Exception {
		final String[] methodsNeeded = { STATUS_CODE, MESSAGE, CAUSE, EXCEPTION};
		if (anObjectWithSimilarStructure != null) {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			Class<?> classFromObject = classLoader.loadClass(className);
			Object instanceReceived = classFromObject.cast(anObjectWithSimilarStructure);

			String methodValue = null;
			Method methodToFind = null;
			for (String methodString : methodsNeeded) {
				methodToFind = classFromObject.getMethod(methodString, new Class<?>[] { String.class });
				if (methodToFind == null) {
					break;
				} else {
					methodValue = (String) methodToFind.invoke(instanceReceived, new Object[] {});
					setTheRightValue(methodString, methodValue);
				}
			}
		}
	}


	
	private void setTheRightValue(final String methodName, final String value) {
		if (methodName.equals(STATUS_CODE)) {
			this.statusCode = value;
		} else if (methodName.equals(MESSAGE)) {
			this.message = value;
		} else if (methodName.equals(CAUSE)) {
			this.cause = value;
		} else if (methodName.equals(EXCEPTION)) {
			this.exception = value;
		}
	}
}
