package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Instanciação da tela para poder manipula-la antes de usa-la
			//Passa o caminho da view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			
			//Carregar a view
			ScrollPane scrollPane = loader.load();
			
			//Deixar scrollpane ajustado na tela
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			//Instanciação da cena passando como parametro o objeto principal da view (ScrollPane da view) 
			mainScene = new Scene(scrollPane);
			
			//Palco da cena tendo como cena principal o mainScene
			primaryStage.setScene(mainScene);
			
			//Título do palco
			primaryStage.setTitle("Aplicação JavaFX");
			
			//Mostrar o palco
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
