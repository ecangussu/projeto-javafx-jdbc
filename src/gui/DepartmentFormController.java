package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	//Dependencia para o departamento
	//entity -> entidade relacionada a este formulário
	private Department entity;
	
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
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	//Controlador passa a ter uma instancia do departamento
	public void setDepartament(Department entity) {
		this.entity = entity;
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
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

}
