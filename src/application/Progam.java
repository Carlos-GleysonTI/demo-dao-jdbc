package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Progam {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
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
		
		System.out.println("\n==== TEST 3: Seller(Clinte) findAll(traz td) ====");
		list = sellerDao.findAll();//usando a mesma lista de cima por isso ñ prec instanciar
		//vamos pecorrer a lista
		for(Seller obj: list) {
			System.out.println(obj);
		}
		
		System.out.println("\n==== TEST 4: Seller(Clinte) insert(Inserir) ====");
		Seller newSeller = new Seller(null, "Carlos", "Carlos@gmail.com", new Date(), 4000.0, department);//department aproveit a instancia de cima
		//chmar o met de inserção
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
		
		System.out.println("\n==== TEST 5: Seller(Clinte) update(Atualizar) ====");
		seller = sellerDao.findById(1);//reauproveitar meu Seller ja instanciado em cima/ minha lista de do id 1
		seller.setName("Martha Waine");// vou altarar a penas no nome do id = 1
		sellerDao.update(seller);//chamo a metodo p atulizar
		System.out.println("Update completed! ");
		
		System.out.println("\n==== TEST 6: Seller(Clinte) delete ====");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed! ");
		
		sc.close();
		
	}

}
