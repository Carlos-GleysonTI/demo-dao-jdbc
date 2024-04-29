package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	//criamos nossas interfaces
	
	//vamos criar metodos para inset(inserir), update(atualizar) , deleteById(delete por id)
	//findById(encontrar por id) e findAll(encontrar td)
	
	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);//so deleta pelo id informado
	Department findById(Integer id);//pega somento pelo id informado
	List<Department> findAll(); // para lista td tenho implemnetar uma lista
}
