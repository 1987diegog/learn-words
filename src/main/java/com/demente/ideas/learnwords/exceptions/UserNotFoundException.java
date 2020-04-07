package com.demente.ideas.learnwords.exceptions;

/**
 * @author 1987diegog
 */
public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
