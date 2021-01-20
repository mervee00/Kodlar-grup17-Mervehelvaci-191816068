package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Polyclinic {
	private int id;
	private String name;

	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Polyclinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Polyclinic() {

	}

	// polikilinik listeler
	public ArrayList<Polyclinic> getList() throws SQLException {
		ArrayList<Polyclinic> list = new ArrayList<>();
		Polyclinic obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM polyclinic");
			while (rs.next()) {
				obj = new Polyclinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();

		}

		return list;
	}

	// polikilinik ekler
	public boolean addPolyclinic(String name) throws SQLException {
		String query = "INSERT INTO polyclinic (name) VALUES (?)";
		boolean xControl = false;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();
			xControl = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (xControl)
			return true;
		else
			return false;
	}

	// poliklinic siler
	public boolean deletePolyclinic(int id) throws SQLException {
		String query = "DELETE FROM polyclinic WHERE id = ?";
		boolean xControl = false;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			xControl = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (xControl)
			return true;
		else
			return false;
	}

	// poliklinik bilgilerini günceller
	public boolean updatePolyclinic(int id, String name) throws SQLException {

		String query = "UPDATE polyclinic SET name = ? WHERE id = ?";
		boolean xControl = false;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			xControl = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (xControl)
			return true;
		else
			return false;
	}

	// Ayný poliklinikteki doktorlarý listeler
	public ArrayList<User> getPolyclinicDoctorList(int polyclinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT u.id,u.tcno,u.password,u.name,u.telephone,u.address,u.type FROM employee e LEFT JOIN user u ON e.doctor_id=u.id WHERE polyclinic_id= "
							+ polyclinic_id);
			while (rs.next()) {
				obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.name"),
						rs.getString("u.password"), rs.getString("u.telephone"), rs.getString("u.address"),
						rs.getString("u.type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
