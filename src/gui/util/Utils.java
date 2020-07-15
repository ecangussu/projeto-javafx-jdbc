package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	//Método responsável por acessar o stage (palco) onde o controller que recebeu o evento (event) está
	//Exemplo: clico em um botão -> método retornará o stage desse botão
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

}
