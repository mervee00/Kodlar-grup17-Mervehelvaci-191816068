package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.Helper;

public class Manager extends User { // User Class'ýndan kalýtým yaptýk
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	// constructor
	public Manager(int id, String tcno, String name, String password,String telephone, String address, String type) {
		super(id, tcno, name, password, telephone, address, type);
	}

	// constructor
	public Manager() {
	}

	// doktorlarý listeler
	public ArrayList<User> getDoctorList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type = 'doctor' ");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),rs.getString("telephone"),rs.getString("address"),
						rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// doktor ekler
	public boolean addDoctor(String tcno, String password, String name,String telephone, String address) throws SQLException {
		String query = "INSERT INTO user  (tcno,password,name,telephone,address,type) VALUES (?,?,?,?,?,?)";
		boolean xControl = false;
		int number = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcno= '" + tcno + "'");
			while (rs.next()) {
				number++;
				Helper.showMsg("Bu TC numarasýna ait bir kayýt bulunmaktadýr!");
				break;
			}
			if (number == 0) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, telephone);
			preparedStatement.setString(5, address);
			preparedStatement.setString(6, "doctor");
			preparedStatement.executeUpdate();
			xControl = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (xControl)
			return true;
		else
			return false;
	}

	// doktor siler
	public boolean deleteDoctor(int id) throws SQLException {
		String query = "DELETE FROM user WHERE id = ?";
		boolean xControl = false;
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

	

	// doktorlara poliklinik ekler
	public boolean addEmployee(int doctor_id, int polyclinic_id ) throws SQLException {
		String query = "INSERT INTO employee (doctor_id,polyclinic_id) VALUES (?,?)";
		boolean xControl = false;
		int number = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM employee WHERE polyclinic_id=" + polyclinic_id + " AND doctor_id=" + doctor_id );
			while (rs.next()) {
				number++;
			}
			if (number == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setInt(2, polyclinic_id);
				preparedStatement.executeUpdate();
             }
			xControl = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (xControl)
			return true;
		else
			return false;
	}
	// poliklinik siler
		public boolean deletePolyclinic(int id) throws SQLException {
			String query = "DELETE FROM polyclinic WHERE id = ?";
			boolean xControl = false;
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
	
	// Ayný poliklinikteki doktorlarý listeler.
		public ArrayList<User> getPolyclinicDoctorList(int polyclinic_id) throws SQLException {
			ArrayList<User> list = new ArrayList<>();
			User obj;
			try {
				st = con.createStatement();
				rs = st.executeQuery("SELECT u.id,u.tcno,u.password,u.name,u.telephone,u.address,u.type FROM employee e LEFT JOIN user u ON e.doctor_id=u.id WHERE polyclinic_id= "+polyclinic_id);
				while (rs.next()) {
					obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.name"), rs.getString("u.password"),rs.getString("u.telephone"),rs.getString("u.address"),
							rs.getString("u.type"));
					list.add(obj);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;
		}
		
		// Arama sonucu doktorlarý listeler.
		public ArrayList<User> getDoctorSearch(String name) throws SQLException {

			ArrayList<User> list = new ArrayList<>();
			User obj;
			try {
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM user WHERE type= 'doctor' AND name like '"+name+"%'");
				while (rs.next()) {
					obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),rs.getString("telephone"),rs.getString("address"),
							rs.getString("type"));
					list.add(obj);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;
		}
}
