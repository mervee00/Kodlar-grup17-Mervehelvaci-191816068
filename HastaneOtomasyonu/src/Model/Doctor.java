package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User {
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Doctor() {
		super();
	}

	public Doctor(int id, String tcno, String name, String password, String telephone, String address, String type) {
		super(id, tcno, name, password,telephone,address,type);
	}

	// doktor bilgilerini günceller
		public boolean updateDoctor(int id, String tcno, String password, String name,String telephone, String address) throws SQLException {

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
		
    //Randevu ekler.
	public boolean addWorkHour(int doctor_id, String doctor_name, String work_date) throws SQLException {
		String query = "INSERT INTO workhour (doctor_id,doctor_name,work_date) VALUES (?,?,?)";
		boolean xControl = false;
		int number = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workhour WHERE status='a' AND doctor_id= " + doctor_id
					+ " AND work_date='" + work_date + "'");
			while (rs.next()) {
				number++;
			}
			if (number == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setString(3, work_date);
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
	
		
		// Randevuyu siler.
		public boolean deleteWorkHour(int id) throws SQLException {
			String query = "DELETE FROM workhour WHERE id = ?";
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

}
