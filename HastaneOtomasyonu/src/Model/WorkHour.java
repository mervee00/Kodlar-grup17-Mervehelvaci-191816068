package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class WorkHour {
	private int id,doctor_id;
	private String doctor_name,work_date,status;
	
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public WorkHour() {
		
	}
	
	public WorkHour(int id, int doctor_id, String doctor_name, String work_date, String status) {
		this.id = id;
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.work_date = work_date;
		this.status = status;
	}
	
	// doktorun tarihlerini listeler
			public ArrayList<WorkHour> getWorkHourList(int doctor_id) throws SQLException {
				ArrayList<WorkHour> list = new ArrayList<>();
				WorkHour obj;
				try {
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM workhour WHERE status ='a' AND doctor_id = "+doctor_id);
					while (rs.next()) {
						obj = new WorkHour();
						obj.setId(rs.getInt("id"));
						obj.setDoctor_id(rs.getInt("doctor_id"));
						obj.setDoctor_name(rs.getString("doctor_name"));	
						obj.setWork_date(rs.getString("work_date"));
						obj.setStatus(rs.getString("status"));
						list.add(obj);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          return list;
			}
			
			public ArrayList<WorkHour> getWorkHourSearchList(int doctor_id,String date) throws SQLException {
				ArrayList<WorkHour> list = new ArrayList<>();
				WorkHour obj;
				try {
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM workhour WHERE  doctor_id = "+doctor_id+" AND work_date like '"+date+"%'"+" AND status ='a' "); 
					while (rs.next()) {
						obj = new WorkHour();
						obj.setId(rs.getInt("id"));
						obj.setDoctor_id(rs.getInt("doctor_id"));
						obj.setDoctor_name(rs.getString("doctor_name"));					
						obj.setWork_date(rs.getString("work_date"));
						obj.setStatus(rs.getString("status"));	
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
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

	

}
