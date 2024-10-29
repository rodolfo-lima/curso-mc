package com.rlima.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException{ //classe de excecao personalizada
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super(msg); // reaproveitando a infraestrutura de esxecoes do java
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg);
	}
	

}
