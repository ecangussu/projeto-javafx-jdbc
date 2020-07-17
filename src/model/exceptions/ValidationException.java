package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
	//Cole��o que ir� conter todos os erros poss�veis, independente do campo
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * map = cole��o de pares chave-valor
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
	
	//Adicionar um elemento a cole��o map
	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}

}
