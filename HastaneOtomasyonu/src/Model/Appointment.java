package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Appointment {
  private int id,doctorID,patientID;
  private String doctorName,patientName,appDate;
  
  DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
public Appointment(){
	 
  }
public Appointment(int id, int doctorID, int patientID, String doctorName, String patientName, String appDate) {
	super();
	this.id = id;
	this.doctorID = doctorID;
	this.patientID = patientID;
	this.doctorName = doctorName;
	this.patientName = patientName;
	this.appDate = appDate;
}

//polikilinik listeler
		public ArrayList<Appointment> getPatientList(int patientID) throws SQLException {
			ArrayList<Appointment> list = new ArrayList<>();
			Appointment obj;
			Connection con = conn.connDb();
			try {
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM appointment WHERE patient_id = "+ patientID);
				while (rs.next()) {
					obj = new Appointment();
					obj.setId(rs.getInt("id"));
					obj.setDoctorID(rs.getInt("doctor_id"));
					obj.setDoctorName(rs.getString("doctor_name"));
					obj.setPatientID(rs.getInt("patient_id"));
					obj.setPatientName(rs.getString("patient_name"));
					obj.setAppDate(rs.getString("app_date"));
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
		
		//Hastanýn randevularýndan aradýgý günü listeler.
		public ArrayList<Appointment> getPatientSearchList(int patientID,String date) throws SQLException {
			ArrayList<Appointment> list = new ArrayList<>();
			Appointment obj;
			Connection con = conn.connDb();
			try {
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM appointment WHERE patient_id = "+ patientID+" AND app_date like '"+date+"%'");
				while (rs.next()) {
					obj = new Appointment();
					obj.setId(rs.getInt("id"));
					obj.setDoctorID(rs.getInt("doctor_id"));
					obj.setDoctorName(rs.getString("doctor_name"));
					obj.setPatientID(rs.getInt("patient_id"));
					obj.setPatientName(rs.getString("patient_name"));
					obj.setAppDate(rs.getString("app_date"));
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

		//polikilinik listeler
				public ArrayList<Appointment> getDoctorList(int doctorID) throws SQLException {
					ArrayList<Appointment> list = new ArrayList<>();
					Appointment obj;
					Connection con = conn.connDb();
					try {
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id = "+doctorID);
						while (rs.next()) {
							obj = new Appointment();
							obj.setId(rs.getInt("id"));
							obj.setDoctorID(rs.getInt("doctor_id"));
							obj.setDoctorName(rs.getString("doctor_name"));
							obj.setPatientID(rs.getInt("patient_id"));
							obj.setPatientName(rs.getString("patient_name"));
							obj.setAppDate(rs.getString("app_date"));
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
				

				
				//doktorun dolu randevularýný listeler
				public ArrayList<Appointment> getDoctorAppointmentList(int doctorID) throws SQLException {
					ArrayList<Appointment> list = new ArrayList<>();
					Appointment obj;
					Connection con = conn.connDb();
					try {
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id = "+ doctorID);
						while (rs.next()) {
							obj = new Appointment();
							obj.setId(rs.getInt("id"));
							obj.setDoctorID(rs.getInt("doctor_id"));
							obj.setDoctorName(rs.getString("doctor_name"));
							obj.setPatientID(rs.getInt("patient_id"));
							obj.setPatientName(rs.getString("patient_name"));
							obj.setAppDate(rs.getString("app_date"));
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
				
				//Doktorun dolu randevularýndan aradýgý günü listeler
				public ArrayList<Appointment> getDoctorAppointmentSearchList(int doctorID,String date) throws SQLException {
					ArrayList<Appointment> list = new ArrayList<>();
					Appointment obj;
					Connection con = conn.connDb();
					try {
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id = "+ doctorID+" AND app_date like '"+date+"%'");
						while (rs.next()) {
							obj = new Appointment();
							obj.setId(rs.getInt("id"));
							obj.setDoctorID(rs.getInt("doctor_id"));
							obj.setDoctorName(rs.getString("doctor_name"));
							obj.setPatientID(rs.getInt("patient_id"));
							obj.setPatientName(rs.getString("patient_name"));
							obj.setAppDate(rs.getString("app_date"));
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
				
				//bütün dolu randevularýný listeler
				public ArrayList<Appointment> getManagerAppointmentList() throws SQLException {
					ArrayList<Appointment> list = new ArrayList<>();
					Appointment obj;
					Connection con = conn.connDb();
					try {
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM appointment ");
						while (rs.next()) {
							obj = new Appointment();
							obj.setId(rs.getInt("id"));
							obj.setDoctorID(rs.getInt("doctor_id"));
							obj.setDoctorName(rs.getString("doctor_name"));
							obj.setPatientID(rs.getInt("patient_id"));
							obj.setPatientName(rs.getString("patient_name"));
							obj.setAppDate(rs.getString("app_date"));
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
				
				//Doktor ismine göre arama yapar.
				public ArrayList<Appointment> getManagerAppointmentSearchList(String name) throws SQLException {
					ArrayList<Appointment> list = new ArrayList<>();
					Appointment obj;
					Connection con = conn.connDb();
						try {
							st = con.createStatement();
							rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_name like '"+name+"%'");// 
							while (rs.next()) {
								obj = new Appointment();
								obj.setId(rs.getInt("id"));
								obj.setDoctorID(rs.getInt("doctor_id"));
								obj.setDoctorName(rs.getString("doctor_name"));
								obj.setPatientID(rs.getInt("patient_id"));
								obj.setPatientName(rs.getString("patient_name"));
								obj.setAppDate(rs.getString("app_date"));
								list.add(obj);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}finally {
						st.close();
						rs.close();
						con.close();

					}

					return list;
				}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getDoctorID() {
	return doctorID;
}
public void setDoctorID(int doctorID) {
	this.doctorID = doctorID;
}
public int getPatientID() {
	return patientID;
}
public void setPatientID(int patientID) {
	this.patientID = patientID;
}
public String getDoctorName() {
	return doctorName;
}
public void setDoctorName(String doctorName) {
	this.doctorName = doctorName;
}
public String getPatientName() {
	return patientName;
}
public void setPatientName(String patientName) {
	this.patientName = patientName;
}
public String getAppDate() {
	return appDate;
}
public void setAppDate(String appDate) {
	this.appDate = appDate;
}
  

}
