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
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	//Tipo da entidade e tipo da coluna
	private TableColumn<Department, Integer> tableColumId;
	
	@FXML
	private TableColumn<Department, String> tableColumName;
	
	@FXML
	private Button btNew;
	
	//Os departamentos serao carregados na obsList
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("Apertou o botão btNovo");
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	//Padrão JavaFX para iniciar o comportamento das colunas
	private void initializeNodes() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//Completar a janela com o TableView (tableview acompanha a altura da janela)
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	//Método responsável por acessar o serviço (service) carregar os dados (departamentos) e joga-los na variavel no obsList	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Serviço está nulo");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}

}
