package com.hsifeulbhsifder.sigma.engine.utils;

/**
 * Basic exception class for engine, terminates program
 * @author Zaeem
 * @version 1.0
 */
public class SigmaException extends RuntimeException{
	//TODO: make methods call all terminate methods
	public SigmaException(String message) {
		super(message);
		System.err.println(message);
		this.printStackTrace();
		System.exit(-1);
	}

	public SigmaException(Throwable t) {
		super(t);
		this.printStackTrace();
		System.exit(-1);
	}

	public SigmaException(String message, Throwable t) {
		super(message, t);
		System.err.println(message);
		this.printStackTrace();
		System.exit(-1);
	}
}
