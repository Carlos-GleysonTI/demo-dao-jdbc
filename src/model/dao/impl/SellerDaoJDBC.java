package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//metodo p inserir no banco de dados via aplicação
	@Override
	public void insert(Seller obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "  
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);//vamos retornar o id "RETURN_GENERATED_KEYS"
			
			//vamos configurar as ????
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));//tenho q instanciar pacate de Data
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			//vamos executar a query mostrando numero de linhaa afetadas
			int rowsAffected = st.executeUpdate(); //executeUpdate() executa a query
			
			// se n° linha for > 0 indica q inseriu
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);//gerado
					obj.setId(id);
				}
				//so posso fechar ele dentro if RS pq ele nao ta fora do TRY
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! no lines affected! ");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	//atualizar dados do banco
	@Override
	public void update(Seller obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"UPDATE seller " 
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, "
					+ "DepartmentId = ? "  
					+ " WHERE Id = ?");//vamos retornar o id "RETURN_GENERATED_KEYS"
			
			//vamos configurar as ????
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));//tenho q instanciar pacate de Data
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());//id o vendedor;
			
			st.executeUpdate(); //executeUpdate() executa a query
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	//para deletar
	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			st.setInt(1, id);		
			
			st.executeUpdate();
			
			/*vamos incluir linha pra testar 
			int rows = st.executeUpdate();
			
			if(rows == 0) {
				throw new DbException("Not exit! ");
			}*/
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		DB.closeStatement(st);
	}

	//metodo p buscar pelo ID 
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

	//Função Seller(Cliente) com atributos do banco e associado c Dep
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

	//Função Department com atributos do banco
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		//tem q ficar igual esta no banco os campos dos atributos
		
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	//findAll() - buscar tudo
	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null; //traz em formato de tabelas
		
		try{
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			//st.setInt(1, department.getId()); //ñ preci pq não usei ? no SQL
			
			//traz as informa do banco
			rs = st.executeQuery();
			
			//minha lista de clientes
			List<Seller> list = new ArrayList<>();
			
			//vamos armarzenar um id dep dentro map p verificar se ja tem um igual
			Map<Integer, Department> map = new HashMap<>();
			
			//vai traz em lista e ordem temos q usar while para percorrer
			while(rs.next()) {
				
				// aqui eu armazendo o id do depar dentro map para poder verifi
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					//função com campos do DATABASE 
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				//pq tem uma associação com department
				Seller obj = instantiateSeller(rs,dep);
				list.add(obj);
				
			}
			
			return list;
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

	//metodo p buscar pelo name do  Department em ordem alfabetica
	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null; //traz em formato de tabelas
		
		try{
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			
			//traz as informa do banco
			rs = st.executeQuery();
			
			//minha lista de clientes
			List<Seller> list = new ArrayList<>();
			
			//vamos armarzenar um id dep dentro map p verificar se ja tem um igual
			Map<Integer, Department> map = new HashMap<>();
			
			//vai traz em lista e ordem temos q usar while para percorrer
			while(rs.next()) {
				
				// aqui eu armazendo o id do depar dentro map para poder verifi
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					//função com campos do DATABASE 
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				//pq tem uma associação com department
				Seller obj = instantiateSeller(rs,dep);
				list.add(obj);
				
			}
			
			return list;
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
}
