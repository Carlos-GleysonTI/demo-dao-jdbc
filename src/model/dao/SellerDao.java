package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {

	//criamos nossas interfaces
	
		//vamos criar metodos para inset(inserir), update(atualizar) , deleteById(delete por id)
		//findById(encontrar por id) e findAll(encontrar td)
		
		void insert(Seller obj);
		void update(Seller obj);
		void deleteById(Integer id);//so deleta pelo id informado
		Seller findById(Integer id);//pega somento pelo id informado
		List<Seller> findAll(); // para lista td tenho implemnetar uma lista
		List<Seller> findByDepartment( Department department);//busco pelo id do Department
}
