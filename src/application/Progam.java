package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Progam {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("==== TEST 1: Seller(Clinte) findById(cliente por id) ====");
		Seller seller = sellerDao.findById(3);//me traz o cliente com id = 3
		System.out.println(seller);	
		
		System.out.println("\n==== TEST 2: Seller(Clinte) findByDepartment ====");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		//vamos pecorrer a lista
		for(Seller obj: list) {
			System.out.println(obj);
		}
		
	}

}
