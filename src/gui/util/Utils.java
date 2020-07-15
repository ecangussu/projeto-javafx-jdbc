package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	//M�todo respons�vel por acessar o stage (palco) onde o controller que recebeu o evento (event) est�
	//Exemplo: clico em um bot�o -> m�todo retornar� o stage desse bot�o
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	//Convers�o da string digitada na caixa de texto para Integer
	public static Integer tryParseToInt(String str) {
		//Retorna a string convertida em Integer ou retorna null e nao uma exception
		try {
			return Integer.parseInt(str);			
		}
		catch(NumberFormatException e) {
			return null;
		}
	}

}
