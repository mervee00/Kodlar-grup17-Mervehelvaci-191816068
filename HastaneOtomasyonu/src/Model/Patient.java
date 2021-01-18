package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.Helper;

public class Patient extends User {

	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Patient() {

	}

	public Patient(int id, String tcno, String name, String password, String telephone, String address, String type) {
		super(id, tcno, name, password,telephone, address, type);
	}

	
	//hasta kayýt ekler
	public boolean addRegister(String tcno, String password, String name,String telephone, String address) throws SQLException {
		String query = "INSERT INTO user (tcno,password,name,telephone,address,type) VALUES (?,?,?,?,?,?)";
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
				preparedStatement.setString(6, "patient");
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

	//randevu oluþturur
	public boolean addAppointment(int doctorID, int patientID, String doctorName, String patientName, String appDate)
			throws SQLException {
		String query = "INSERT INTO appointment (doctor_id,doctor_name,patient_id,patient_name,app_date) VALUES (?,?,?,?,?)";
		boolean xControl = false;
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doctorID);
			preparedStatement.setString(2, doctorName);
			preparedStatement.setInt(3, patientID);
			preparedStatement.setString(4, patientName);
			preparedStatement.setString(5, appDate);
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

	// hasta bilgilerini günceller
		public boolean updatePatient(int id, String tcno, String password, String name,String telephone, String address) throws SQLException {

			String query = "UPDATE user SET  tcno = ? , password=? , name = ?, telephone = ?, address = ?  WHERE id = ?";
			boolean xControl = false;
			try {
				st = con.createStatement();
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, tcno);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, telephone);
				preparedStatement.setString(5, address);
				preparedStatement.setInt(6, id);
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
	//doktorun o saatteki randevusunu pasif yapar
	public boolean updateWhourStatus(int doctorID, String wdate) throws SQLException {
		String query = "UPDATE workhour SET status = ? WHERE doctor_id = ? AND work_date = ?";
		boolean xControl = false;
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doctorID);
			preparedStatement.setString(3, wdate);
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
	
	//doktorun o saatteki randevusunu aktif yapar
		public boolean updateWStatus(int doctorID, String wdate) throws SQLException {
			String query = "UPDATE workhour SET status = ? WHERE doctor_id = ? AND work_date = ?";
			boolean xControl = false;
			try {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, "a");
				preparedStatement.setInt(2,doctorID);
				preparedStatement.setString(3, wdate);
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
	
	// randevu siler
		public boolean deleteAppointment(int id) throws SQLException {
			String query = "DELETE FROM appointment WHERE id = ?";
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
		
		
		//doktorun o saatteki randevusunu aktif yapar
				public boolean updateAppointmet(int doctorID, String wdate) throws SQLException {
					String query = "UPDATE workhour SET status = ? WHERE doctor_id = ? AND work_date = ?";
					boolean xControl = false;
					try {
						preparedStatement = con.prepareStatement(query);
						preparedStatement.setString(1, "a");
						preparedStatement.setInt(2, doctorID);
						preparedStatement.setString(3, wdate);
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
}
