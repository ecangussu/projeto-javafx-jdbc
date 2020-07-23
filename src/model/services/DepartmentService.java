package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department obj) {
		//Inserir ou atualizar
		//id = null -> obj nao existe no bd -> inserir novo obj
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		//id != null -> obj ja existe no bd -> atualizar bd
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Department obj) {
		dao.deleteById(obj.getId());
	}

}
