package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
	//Coleção que irá conter todos os erros possíveis, independente do campo
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * map = coleção de pares chave-valor
	 * Primeiro string = chave = nome do campo
	 * Segundo string = valor = msg de erro
	**/
	private Map<String, String> errors = new HashMap<>();
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	//Adicionar um elemento a coleção map
	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}

}
