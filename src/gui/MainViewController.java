package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartamentoService;

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
		//2º parametros - acessar o controller
		//Referencia para o controller dessa view
		loadView("/gui/DepartamentoList.fxml", (DepartamentoListController controller) -> {
			//Injetar a dependencia do service no controller
			controller.setDepartamentoService(new DepartamentoService());
			controller.updateTableView();
		});
	}
	
	public void onMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml", x -> {});
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
			
			//Comando para ativar o método passado como parametro (initializingAction)
			T controller = loader.getController();
			//Comando para executar o metodo passado como parametro
			initializingAction.accept(controller);
		}
		catch (IOException e) {
			Alertas.showAlert("IO Exception", "Erro no carregamento da página", e.getMessage(), AlertType.ERROR);
		}
	}

}
