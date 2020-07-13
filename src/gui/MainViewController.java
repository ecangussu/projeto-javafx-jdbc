package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.DepartmentService;

public class MainViewController implements Initializable {
	//Classe que controla as a��es da tela
	
	//Cria��o dos atributos dos itens de menu (MenuItem)
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	//Cria��o dos m�todos para tratar os eventos do menu
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	public void onMenuItemDepartmentAction() {
		//2� parametros - acessar o controller
		//Referencia para o controller dessa view
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			//Injetar a dependencia do service no controller
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	//synchronized faz com que o processamento nao seja interrompido durante o multitrading
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
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
			//Acrescentando os filhos do vbox da tela sobre
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//Comando para ativar o m�todo passado como parametro (initializingAction)
			T controller = loader.getController();
			//Comando para executar o metodo passado como parametro
			initializingAction.accept(controller);
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro no carregamento da p�gina", e.getMessage(), AlertType.ERROR);
		}
	}

}
