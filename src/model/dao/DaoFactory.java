package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

//classe fabrica de Dao
public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());//tenho instaciar conexão
	}
}
