package com.demente.ideas.learnwords.exceptions;

/**
 * @author 1987diegog
 */
public class InternalServerErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }

}