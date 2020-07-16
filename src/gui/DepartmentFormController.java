package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	//Dependencia para o department
	//entity -> entidade relacionada a este formul�rio
	private Department entity;
	
	//Dependencia para o DepartmentService
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entidade est� vazia!");
		}
		if (service == null) {
			throw new IllegalStateException("Servi�o est� vazio!");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			//Quando o saveOrUpdate finalizar com sucesso ser� necess�rio notificar aos listeners
			notifyDataChangeListeners();
			//Pegando uma referencia da janela atual 
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlert("Erro ao salvar o objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	//Pegar os dados digitados nas caixas de textos da tela e instanciar um departamento com eles
	private Department getFormData() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
	}
	
	//Controlador passa a ter uma instancia do departamento
	public void setDepartament(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	//Outros objetos que implementam o DataChangeListener poderao se inscrever para receber o evento da classe 
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	//Pegar os dados do departamento e popular as caixas de texto do formul�rio
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entidade vazia!");
		}
		//Converte o valor do id (inteiro) para String -> caixas de texto recebem string
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
