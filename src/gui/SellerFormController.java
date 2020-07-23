package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	//Dependencia para o department
	//entity -> entidade relacionada a este formulário
	private Seller entity;
	
	//Dependencia para o SellerService
	private SellerService service;
	
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
			throw new IllegalStateException("Entidade está vazia!");
		}
		if (service == null) {
			throw new IllegalStateException("Serviço está vazio!");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			//Quando o saveOrUpdate finalizar com sucesso será necessário notificar aos listeners
			notifyDataChangeListeners();
			//Pegando uma referencia da janela atual 
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
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
	private Seller getFormData() {
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Erro de validação!");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "O campo não pode ser vazio!");
		}
		obj.setName(txtName.getText());
		
		//Se na coleção de erros existe pelo menos 1 erro a exceção será lançada 
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}
	
	//Controlador passa a ter uma instancia do departamento
	public void setDepartament(Seller entity) {
		this.entity = entity;
	}
	
	public void setSellerService(SellerService service) {
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
	
	//Pegar os dados do departamento e popular as caixas de texto do formulário
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entidade vazia!");
		}
		//Converte o valor do id (inteiro) para String -> caixas de texto recebem string
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	//Responsável por pegar os erros da exceção e joga-los na tela
	private void setErrorMessages(Map<String, String> errors) {
		//Set = conjunto (outra coleção)
		Set<String> fields = errors.keySet();
		
		//Se no conjunto fields existe a chave name
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}

}