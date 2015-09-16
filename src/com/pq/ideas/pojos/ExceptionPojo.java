package com.pq.ideas.pojos;

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


	public ExceptionPojo(final Object pojoObject) {
		setValues(pojoObject);
	}


	public ExceptionPojo(final String statusCode, final String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}


	public ExceptionPojo(final String statusCode, final String message, final String cause, String exception) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.cause = cause;
		this.exception = exception;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(final String statusCode) {
		this.statusCode = statusCode;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(final String message) {
		this.message = message;
	}


	public String getCause() {
		return cause;
	}


	public void setCause(final String cause) {
		this.cause = cause;
	}


	public String getException() {
		return exception;
	}


	public void setException(final String exception) {
		this.exception = exception;
	}


	public void setValues(final Object anObjectWithSimilarStructure) {
		final String[] methodsNeeded = { STATUS_CODE, MESSAGE, CAUSE, EXCEPTION };
		if (anObjectWithSimilarStructure != null) {
			try {
				final String className = anObjectWithSimilarStructure.getClass().getName();
				final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
				Class<?> classFromObject = classLoader.loadClass(className);
				Object instanceReceived = classFromObject.cast(anObjectWithSimilarStructure);

				String methodValue = null;
				Method methodToFind = null;
				for (String methodString : methodsNeeded) {
					methodToFind = classFromObject.getMethod(methodString);
					if (methodToFind == null) {
						break;
					} else {
						methodValue = (String) methodToFind.invoke(instanceReceived, new Object[] {});
						setTheRightValue(methodString, methodValue);
					}
				}
				methodToFind = null;
				methodValue = null;
			} catch (Exception e) {
				// TODO Just duck as values will be null, throw some POCException or log some message?
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
