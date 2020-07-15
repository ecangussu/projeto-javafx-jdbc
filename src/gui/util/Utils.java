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

}
