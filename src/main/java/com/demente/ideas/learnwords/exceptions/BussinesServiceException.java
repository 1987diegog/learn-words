package com.demente.ideas.learnwords.exceptions;

/**
 * @author 1987diegog
 */
public class BussinesServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public BussinesServiceException() {
		super();
	}

	public BussinesServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BussinesServiceException(String message) {
		super(message);
	}

	public BussinesServiceException(Throwable cause) {
		super(cause);
	}
}
