package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Doctor;
import Model.Manager;
import Model.Patient;

import javax.swing.Icon;
import javax.swing.SwingConstants;

public class LoginGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_patientTc;
	private JTextField fld_doktorTc;
	private JPasswordField fld_doktorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_patientPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 432);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_iconLogo = new JLabel(new ImageIcon(getClass().getResource("/logo.png")));
		lbl_iconLogo.setBounds(262, 11, 111, 97);
		w_pane.add(lbl_iconLogo);

		JLabel lbl_hospital = new JLabel("Hastane Y\u00F6netim Sistemi");
		lbl_hospital.setBounds(208, 103, 229, 24);
		lbl_hospital.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		w_pane.add(lbl_hospital);

		JLabel lbl_welcome = new JLabel("Ho\u015Fgeldiniz");
		lbl_welcome.setBounds(262, 127, 111, 24);
		lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 19));
		w_pane.add(lbl_welcome);

		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 156, 582, 235);
		w_pane.add(w_tabpane);

		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta Giriþi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);

		JLabel lbl_hTC = new JLabel("TC Numaran\u0131z:");
		lbl_hTC.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lbl_hTC.setBounds(164, 35, 152, 24);
		w_hastaLogin.add(lbl_hTC);

		JLabel lbl_hPass = new JLabel("\u015Eifre:");
		lbl_hPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lbl_hPass.setBounds(167, 82, 152, 24);
		w_hastaLogin.add(lbl_hPass);

		fld_patientTc = new JTextField();
		fld_patientTc.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_patientTc.setBounds(340, 32, 207, 34);
		w_hastaLogin.add(fld_patientTc);
		fld_patientTc.setColumns(10);

		JButton btn_register = new JButton("Kay\u0131t Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI register = new RegisterGUI();
				register.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_register.setBounds(165, 135, 186, 40);
		w_hastaLogin.add(btn_register);

		//Hasta Giriþ
		JButton btn_patientLogin = new JButton("Giri\u015F Yap");
		btn_patientLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_patientTc.getText().length() == 0 || fld_patientPass.getText().length() == 0) {
					Helper.showMsg("complete");
				} else {
					boolean xControl = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");// sql deki tum userlarý cagýrýr

						while (rs.next()) { // tek tek kontrol eder
							if (fld_patientTc.getText().equals(rs.getString("tcno"))
									&& fld_patientPass.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("patient")) {
									Patient patient = new Patient();
									patient.setId(rs.getInt("id"));
									patient.setPassword(rs.getString("password"));
									patient.setTcno(rs.getString("tcno"));
									patient.setName(rs.getString("name"));
									patient.setTelephone(rs.getString("telephone"));
									patient.setAddress(rs.getString("address"));
									patient.setType(rs.getString("type"));
									PatientGUI pGUI = new PatientGUI(patient);
									pGUI.setVisible(true);
									dispose();
									xControl=false;
								}

							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (xControl) {
						Helper.showMsg("Böyle bir hasta bulunamadý! Lütfen kayýt olunuz!");
					}

				}

			}

		});
		btn_patientLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_patientLogin.setBounds(361, 135, 186, 40);
		w_hastaLogin.add(btn_patientLogin);

		JLabel lbl_patienticon = new JLabel(new ImageIcon(getClass().getResource("/patientlogin.png").getFile()));
		//JLabel lbl_patienticon = new JLabel(new ImageIcon(getClass().getResource("patientlogin.png")));
		lbl_patienticon.setHorizontalAlignment(SwingConstants.LEADING);
		lbl_patienticon.setBounds(21, 35, 115, 140);
		w_hastaLogin.add(lbl_patienticon);

		fld_patientPass = new JPasswordField();
		fld_patientPass.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_patientPass.setBounds(340, 77, 207, 34);
		w_hastaLogin.add(fld_patientPass);

		JPanel w_doktorLogin = new JPanel();
		w_doktorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Giriþi", null, w_doktorLogin, null);
		w_doktorLogin.setLayout(null);

		JLabel lbl_dTC = new JLabel("TC Numaran\u0131z:");
		lbl_dTC.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lbl_dTC.setBounds(166, 33, 152, 24);
		w_doktorLogin.add(lbl_dTC);

		JLabel lbl_dPass = new JLabel("\u015Eifre:");
		lbl_dPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lbl_dPass.setBounds(166, 90, 152, 24);
		w_doktorLogin.add(lbl_dPass);

		fld_doktorTc = new JTextField();
		fld_doktorTc.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_doktorTc.setColumns(10);
		fld_doktorTc.setBounds(344, 30, 207, 34);
		w_doktorLogin.add(fld_doktorTc);

		//Doktor giriþ
		JButton btn_doktorLogin = new JButton("Giri\u015F Yap");
		btn_doktorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doktorTc.getText().length() == 0 || fld_doktorPass.getText().length() == 0) {
					Helper.showMsg("complete");
				} else {
					boolean xControl = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");// sql deki tum userlarý cagýrýr
						while (rs.next()) { // tek tek kontrol eder
							if (fld_doktorTc.getText().equals(rs.getString("tcno"))
									&& fld_doktorPass.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("manager")) {
									Manager bhekim = new Manager();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword(rs.getString("password"));
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setTelephone(rs.getString("telephone"));
									bhekim.setAddress(rs.getString("address"));
									bhekim.setType(rs.getString("type"));
									ManagerGUI bGUI = new ManagerGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
									xControl=false;
								}
								if (rs.getString("type").equals("doctor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword(rs.getString("password"));
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setTelephone(rs.getString("telephone"));
									doctor.setAddress(rs.getString("address"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
									xControl=false;
								}

							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (xControl) {
						Helper.showMsg("Böyle bir doktor bulunamadý! Bilgilerinizi kontrol edin!");
					}
				}
			}
		});
		btn_doktorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_doktorLogin.setBounds(166, 135, 381, 47);
		w_doktorLogin.add(btn_doktorLogin);

		fld_doktorPass = new JPasswordField();
		fld_doktorPass.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_doktorPass.setBounds(344, 90, 207, 34);
		w_doktorLogin.add(fld_doktorPass);

		JLabel lbl_doctoricon = new JLabel(new ImageIcon(getClass().getResource("/doctorlogin.png")));
		lbl_doctoricon.setBounds(23, 33, 118, 140);
		w_doktorLogin.add(lbl_doctoricon);
		
		JButton btnNewButton = new JButton(new ImageIcon(getClass().getResource("/help.png")));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Helper.helpMsg("login");
			}
		});
		btnNewButton.setBounds(554, 11, 38, 35);
		w_pane.add(btnNewButton);
	}
}
