package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.DBConnection;
import Helper.Helper;

public class User {
	private int id;
	String tcno, name, password, telephone, address, type;
	DBConnection conn = new DBConnection();

	public User(int id, String tcno, String name, String password, String telephone, String address, String type) {
		this.id = id;
		this.tcno = tcno;
		this.name = name;
		this.password = password;
		this.telephone = telephone;
		this.address = address;
		this.type = type;
	}

	public User() {
	}

	public boolean addRegister(String tcno, String password, String name, String telephone, String address, String type)
			throws SQLException {
		String query = "INSERT INTO user (tcno,password,name,telephone,address,type) VALUES (?,?,?,?,?,?)";
		boolean xControl = false;
		int number = 0;
		try {
			Connection con = conn.connDb();
			PreparedStatement preparedStatement = null;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM user WHERE tcno= '" + tcno + "'");
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
				preparedStatement.setString(6, type);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTcno() {
		return tcno;
	}

	public void setTcno(String tcno) {
		this.tcno = tcno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
