package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Progam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Department obj = new Department(1, "Books");
		Seller seller = new Seller(21, "Carlos", "Carlos@gamil.com", new Date(), 3000.0, obj);	
		*/
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller seller = sellerDao.findById(3);//me traz o cliente com id = 3
		
		//System.out.println(obj);
		System.out.println(seller);
		
		
	}

}
