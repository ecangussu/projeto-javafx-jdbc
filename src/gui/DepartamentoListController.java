package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoService;

public class DepartamentoListController implements Initializable {

	private DepartamentoService service;
	
	@FXML
	private TableView<Departamento> tableViewDepartamento;
	
	@FXML
	//Tipo da entidade e tipo da coluna
	private TableColumn<Departamento, Integer> tableColumId;
	
	@FXML
	private TableColumn<Departamento, String> tableColumNome;
	
	@FXML
	private Button btNovo;
	
	//Os departamentos serao carregados na obsList
	private ObservableList<Departamento> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("Apertou o botão btNovo");
	}
	
	public void setDepartamentoService(DepartamentoService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	//Padrão JavaFX para iniciar o comportamento das colunas
	private void initializeNodes() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		//Completar a janela com o TableView (tableview acompanha a altura da janela)
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}
	
	//Método responsável por acessar o serviço (service) carregar os dados (departamentos) e joga-los na variavel no obsList	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		List<Departamento> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
	}

}
