package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {
	//Classe que controla as a��es da tela
	
	//Cria��o dos atributos dos itens de menu (MenuItem)
	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemSobre;
	
	
	//Cria��o dos m�todos para tratar os eventos do menu
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuItemVendedorAction");
	}
	
	public void onMenuItemDepartamentoAction() {
		System.out.println("onMenuItemDepartamentoAction");
	}
	
	public void onMenuItemSobreAction() {
		System.out.println("onMenuItemSobreAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
