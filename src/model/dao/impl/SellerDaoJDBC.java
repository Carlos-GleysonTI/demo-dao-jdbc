package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	//abri nosa conexão com
	private Connection conn;
	
	//iniciar a conexão
	//bast criar um construtar para se conectar
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;//ja ta conectado
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		//findById(encontrar por id)
		
		PreparedStatement st = null;
		ResultSet rs = null; //traz em formato de tabelas
		
		try{
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "//"as DepName" criar nome da tabela
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			//vamos inf o Id do ?
			
			st.setInt(1, id);
			rs = st.executeQuery();//traz as informa do banco
			
			if(rs.next()) {
				
				//função com campos do DATABASE 
				Department dep = instantiateDepartment(rs);
				//pq tem uma associação com department
				Seller obj = instantiateSeller(rs,dep);
				
				return obj;
				//pq obj pq estamos fazendo uma busca pelo 
				//id do Seller(Cliente) e Dep e so assoação
			}
			
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		//tenhos fechar meus recursos
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	//Metodo Seller(Cliente) com atributos do banco e associado c Dep
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {

		//tem q ficar igual esta no banco
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	//Metodo Department com atributos do banco
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		//tem q ficar igual esta no banco os campos dos atributos
		
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
