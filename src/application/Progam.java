package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Progam {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("==== TEST 1: Seller(Clinte) findById(cliente por id) ====");
		Seller seller = sellerDao.findById(3);//me traz o cliente com id = 3
		System.out.println(seller);		
		
	}

}
