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
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					 "insert INTO seller"
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "values"
					+ " (?, ?, ?, ?, ?)",
					+ Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getSalary());
			st.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
			    int id = rs.getInt(1);
			    seller.setId(id);
				}
					DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Unexpected error! no rows affected");
			}
		} catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName"
					+ " from seller inner join department on seller.DepartmentId  = department.id WHERE seller.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = intatiateDepartment(rs);
				Seller seller = intatiateSeller(rs, dep);
				return seller;
			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller intatiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department intatiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}


	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.*, department.Name as depName " + "from seller inner join department "
							+ "on seller.DepartmentId = department.Id " + "where DepartmentId = ? " + "order by name");

			st.setInt(1, department.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = intatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = intatiateSeller(rs, dep);
				list.add(seller);
			}
			return list;

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.*, department.Name as depName " + "from seller inner join department "
							+ "on seller.DepartmentId = department.Id " + "order by name");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = intatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = intatiateSeller(rs, dep);
				list.add(seller);
			}
			return list;

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
