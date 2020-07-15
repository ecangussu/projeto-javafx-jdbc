package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
	//Event como parametro -> referencia do controller que recebeu o evento
	//A partir do event será possível acessar o stage
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
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
	
	//Quando é criada uma janela de diálogo é necessário informar quem é o stage que criou esta janela
	//obj = objeto do departamento
	//absoluteName = tela que será carregada
	//parentState = stage (palco) da janela atual
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		//Instanciação da janela de diálogo
		//Carregar a janela
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//Referência para o controlador
			//Pega o controller da tela carregada acima (formulario)
			DepartmentFormController controller = loader.getController();
			//Injetando no controlador o departamento
			controller.setDepartament(obj);
			//Injeção de dependencia do DepartmentService no controlador
			controller.setDepartmentService(new DepartmentService());
			//Carregar os dados do obj no formulario
			controller.updateFormData();
			
			//Quando se quer carregar uma janela na frente de outra já carregada é necessário instanciar outro stage (palco) -> um palco na frente do outro
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do departamento");
			dialogStage.setScene(new Scene(pane));
			//Se a janela pode ou nao ser redimensionada 
			dialogStage.setResizable(false);
			//Stage pai da nova janela
			dialogStage.initOwner(parentStage);
			//Se a janela será modal (fica travado na tela -> nao consegue acessar outra janela enquanto nao fecha-la) ou nao
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} 
		catch(IOException e) {
			Alerts.showAlert("IOException", "Erro no carregamento do view", e.getMessage(), AlertType.ERROR);
		}
	}

}
