package com.hsifeulbhsifder.sigma.engine.utils;


public class SigmaException extends RuntimeException{
	
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
