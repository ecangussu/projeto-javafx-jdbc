package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	//Classe que controla as ações da tela
	
	//Criação dos atributos dos itens de menu (MenuItem)
	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemSobre;
	
	
	//Criação dos métodos para tratar os eventos do menu
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuItemVendedorAction");
	}
	
	public void onMenuItemDepartamentoAction() {
		System.out.println("onMenuItemDepartamentoAction");
	}
	
	public void onMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	//synchronized faz com que o processamento nao seja interrompido durante o multitrading
	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			//Referencia a cena da tela principal
			Scene mainScene = Main.getMainScene();
			//Referencia ao vbox da tela principal
			//getRoot() pega o primeiro elemento da view (scrollpane)
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			//Acrescentar nos filhos do vbox da tela principal os filhos do vbox da janela sobre
			//"Salvando" os filhos do vbox da tela principal numa variavel
			Node mainMenu = mainVBox.getChildren().get(0);
			//Limpando o vbox da tela principal
			mainVBox.getChildren().clear();
			//Adicionando os filhos do vbox da tela principal
			mainVBox.getChildren().add(mainMenu);
			//Acrescentando os filhoos do vbox da tela sobre
			mainVBox.getChildren().addAll(newVBox.getChildren());			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro no carregamento da página", e.getMessage(), AlertType.ERROR);
		}
	}

}
