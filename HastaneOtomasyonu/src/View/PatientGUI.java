package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Patient;
import Model.Polyclinic;
import Model.WorkHour;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

public class PatientGUI extends JFrame {

	private JPanel contentPane;
	private static Patient patient = new Patient();
	private static Polyclinic polyclinic = new Polyclinic();
	private static WorkHour whour = new WorkHour();
	private static Appointment appoint = new Appointment();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDoctorID = 0;

	private String selectDoctorName = null;
	private JTable table;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private String randevuId = null;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientGUI frame = new PatientGUI(patient);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public PatientGUI(Patient patient) throws SQLException {
		setTitle("Hastane Y\u00F6netim Sistemi");

		// Doktor model
		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];

		// çalýþma saati model
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[3];
		colWhour[0] = "ID";
		colWhour[1] = "Doctor Adý";
		colWhour[2] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[3];

		// randevu model
		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doctor Adý";
		colAppoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];

		for (int i = 0; i < appoint.getPatientList(patient.getId()).size(); i++) {
			appointData[0] = appoint.getPatientList(patient.getId()).get(i).getId();
			appointData[1] = appoint.getPatientList(patient.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getPatientList(patient.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_patienticon = new JLabel(new ImageIcon(getClass().getResource("/patient.png")));
		lbl_patienticon.setBounds(10, 0, 135, 119);
		contentPane.add(lbl_patienticon);

		JLabel lbl_welcome = new JLabel("Hoþgeldiniz, Sayýn " + patient.getName());
		lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_welcome.setBounds(150, 32, 264, 42);
		contentPane.add(lbl_welcome);

		JButton btn_exit = new JButton(new ImageIcon(getClass().getResource("/cancel.png")));
		btn_exit.setBackground(Color.WHITE);
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();

			}
		});
		btn_exit.setBounds(676, 13, 48, 35);
		contentPane.add(btn_exit);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 120, 714, 380);
		contentPane.add(w_tab);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		w_tab.addTab("Randevu Oluþtur", null, panel, null);
		panel.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 22, 262, 319);
		panel.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		JLabel lbl_doctorList = new JLabel("Doktor Listesi");
		lbl_doctorList.setForeground(new Color(0, 0, 0));
		lbl_doctorList.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_doctorList.setBounds(10, 0, 130, 24);
		panel.add(lbl_doctorList);

		JComboBox combo_polyclinic = new JComboBox();
		combo_polyclinic.setBounds(282, 50, 143, 30);
		combo_polyclinic.addItem("--Poliklinik Seç--");
		for (int i = 0; i < polyclinic.getList().size(); i++) {
			combo_polyclinic
					.addItem(new Item(polyclinic.getList().get(i).getId(), polyclinic.getList().get(i).getName()));
		}
		combo_polyclinic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (combo_polyclinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < polyclinic.getPolyclinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = polyclinic.getPolyclinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = polyclinic.getPolyclinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
			}

		});

		panel.add(combo_polyclinic);

		JLabel lbl_polyclinicList = new JLabel("Poliklinik Listesi");
		lbl_polyclinicList.setForeground(Color.BLACK);
		lbl_polyclinicList.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_polyclinicList.setBounds(282, 22, 130, 24);
		panel.add(lbl_polyclinicList);

		JLabel lbl_policilinicName_1 = new JLabel("Doktor Se\u00E7");
		lbl_policilinicName_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_policilinicName_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_policilinicName_1.setBounds(282, 179, 143, 21);
		panel.add(lbl_policilinicName_1);

		JButton btn_addDoctor = new JButton(new ImageIcon(getClass().getResource("/choosedoctor.png")));
		btn_addDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table_doctor.getSelectedRow();
				if (selectRow >= 0) {
					String selPolyclinic = table_doctor.getModel().getValueAt(selectRow, 0).toString();
					int selPolyclinicID = Integer.parseInt(selPolyclinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < whour.getWorkHourList(selPolyclinicID).size(); i++) {
							whourData[0] = whour.getWorkHourList(selPolyclinicID).get(i).getId();
							whourData[1] = whour.getWorkHourList(selPolyclinicID).get(i).getDoctor_name();
							whourData[2] = whour.getWorkHourList(selPolyclinicID).get(i).getWork_date();
							whourModel.addRow(whourData);
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_whour.setModel(whourModel);
					selectDoctorID = selPolyclinicID;
					selectDoctorName = table_doctor.getModel().getValueAt(selectRow, 1).toString();
					// System.out.println(selectDoctorID+" "+selectDoctorName);

				} else {
					Helper.showMsg("Lütfen önce bir poliklinik sonra doktor seçiniz!");
				}
			}

		});
		btn_addDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addDoctor.setBounds(314, 106, 75, 73);
		panel.add(btn_addDoctor);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(437, 22, 262, 319);
		panel.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		table_whour.getColumnModel().getColumn(0).setMaxWidth(5);
		// table_Whour.getColumnModel().getColumn(1).setMaxWidth(100);

		JLabel lbl_doctorList_1 = new JLabel("Randevu Listesi");
		lbl_doctorList_1.setForeground(Color.BLACK);
		lbl_doctorList_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_doctorList_1.setBounds(437, 0, 130, 24);
		panel.add(lbl_doctorList_1);

		JLabel lbl_appointment = new JLabel("Randevu Al");
		lbl_appointment.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_appointment.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_appointment.setBounds(282, 283, 143, 21);
		panel.add(lbl_appointment);

		JButton btn_addAppointment = new JButton(new ImageIcon(getClass().getResource("/appointment.png")));
		btn_addAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table_whour.getSelectedRow();
				if (selectRow >= 0) {
					String seldate = table_whour.getModel().getValueAt(selectRow, 2).toString();
					try {
						boolean control = patient.addAppointment(selectDoctorID, patient.getId(), selectDoctorName,
								patient.getName(), seldate);
						if (control) {
							Helper.showMsg("success");
							patient.updateWhourStatus(selectDoctorID, seldate);
							updateWhourModel(selectDoctorID);
							updateAppointModel(patient.getId());
						} else {
							Helper.showMsg("error");
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen geçerli bir randevu saati seçiniz!");
				}
			}
		});
		btn_addAppointment.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addAppointment.setBounds(314, 211, 75, 73);
		panel.add(btn_addAppointment);

		JPanel panel_apppoint = new JPanel();
		panel_apppoint.setBackground(Color.WHITE);
		w_tab.addTab("Randevularým", null, panel_apppoint, null);
		panel_apppoint.setLayout(null);

		JScrollPane scroll_apppoint = new JScrollPane();
		scroll_apppoint.setBounds(10, 53, 689, 288);
		panel_apppoint.add(scroll_apppoint);

		table = new JTable(appointModel);
		scroll_apppoint.setViewportView(table);

		JButton btn_delete = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table.getSelectedRow();
				if (selectRow >= 0) {
					if (Helper.confirm("sure")) {
						String seldate = table.getModel().getValueAt(selectRow, 2).toString();
						String select = table.getModel().getValueAt(selectRow, 0).toString();
						int selectID = Integer.parseInt(select);
						try {
							boolean control = patient.deleteAppointment(selectID);
							if (control) {
								Helper.showMsg("success");
								patient.updateWStatus(selectDoctorID, seldate);
								updateAppointModel(patient.getId());
								updateWhourModel(selectDoctorID);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();

						}
					} else {
						Helper.showMsg("cancel");
					}
				} else {
					Helper.showMsg("Lütfen geçerli bir randevu seçiniz!");
				}
			}
		});
		btn_delete.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_delete.setBounds(656, 7, 43, 35);
		panel_apppoint.add(btn_delete);
		
		JLabel lbl_dsearch_1_3 = new JLabel("Arama");
		lbl_dsearch_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_dsearch_1_3.setBounds(10, 14, 53, 21);
		panel_apppoint.add(lbl_dsearch_1_3);
		
		JDateChooser select_wdate_2 = new JDateChooser();
		select_wdate_2.getCalendarButton().setForeground(Color.BLACK);
		select_wdate_2.getCalendarButton().setBackground(new Color(135, 206, 235));
		select_wdate_2.setBounds(65, 11, 226, 30);
		panel_apppoint.add(select_wdate_2);
		
		JButton btn_search_2_1 = new JButton("Ara");
		btn_search_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_wdate_2.getDate());
				} catch (Exception e2) {
					// TOOO: handle exception
				}
				if (date.length() == 0) {
					Helper.showMsg("Lüften geçerli bir tarih þeçiniz!");
				} else {
					String selectDate = date;
					DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
					clearModel.setRowCount(0);
					int patientID = 0;
					try {
						patientID = appoint.getPatientList(patient.getId()).get(0).getPatientID();
						for (int i = 0; i < appoint.getPatientSearchList(patientID,date).size(); i++) {
							appointData[0] = appoint.getPatientSearchList(patientID,date).get(i).getId();
							appointData[1] = appoint.getPatientSearchList(patientID,date).get(i).getDoctorName();
							appointData[2] = appoint.getPatientSearchList(patientID,date).get(i).getAppDate();
							appointModel.addRow(appointData);
						}


					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btn_search_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_2_1.setBounds(301, 11, 110, 30);
		panel_apppoint.add(btn_search_2_1);
		
		JButton btn_search_1_1_1 = new JButton("Sil");
		btn_search_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					 int patientID = appoint.getPatientList(patient.getId()).get(0).getPatientID();
					updateAppointModel(patientID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}								
			}
		});
		btn_search_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_1_1_1.setBounds(421, 11, 112, 30);
		panel_apppoint.add(btn_search_1_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		w_tab.addTab("Bilgilerim", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lbl_dName_3_1 = new JLabel("Bilgilerim:");
		lbl_dName_3_1.setFont(new Font("Impact", Font.PLAIN, 17));
		lbl_dName_3_1.setBounds(46, 11, 82, 21);
		panel_1.add(lbl_dName_3_1);
		
		JLabel lblNewLabel = new JLabel("Ad Soyad: "+patient.getName());
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel.setBackground(new Color(204, 204, 255));
		lblNewLabel.setBounds(46, 34, 230, 35);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tc: "+patient.getTcno());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_1.setBackground(new Color(204, 204, 255));
		lblNewLabel_1.setBounds(46, 75, 254, 35);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u015Eifre: "+patient.getPassword());
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_2.setBackground(new Color(204, 204, 255));
		lblNewLabel_2.setBounds(46, 115, 254, 35);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Telefon: "+patient.getTelephone());
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_3.setBackground(new Color(204, 204, 255));
		lblNewLabel_3.setBounds(46, 154, 254, 35);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Adres: "+patient.getAddress());
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_4.setBackground(new Color(204, 204, 255));
		lblNewLabel_4.setBounds(46, 190, 254, 35);
		panel_1.add(lblNewLabel_4);
		
		JPanel panel_3 = new JPanel();
		panel_3.setToolTipText("");
		panel_3.setBackground(new Color(255, 255, 153));
		panel_3.setBounds(45, 34, 255, 206);
		panel_1.add(panel_3);
		
		JButton btn_dUpdate_1 = new JButton(new ImageIcon(getClass().getResource("/arrangement.png")));
		btn_dUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(patient.getName());
				textField_1.setText(patient.getTcno());
				textField_2.setText(patient.getPassword());
				textField_3.setText(patient.getTelephone());
				textField_4.setText(patient.getAddress());
			}
		});
		btn_dUpdate_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_dUpdate_1.setBackground(Color.LIGHT_GRAY);
		btn_dUpdate_1.setBounds(310, 63, 58, 46);
		panel_1.add(btn_dUpdate_1);
		
		JLabel lbl_dName_3_2 = new JLabel("D\u00FCzenle");
		lbl_dName_3_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3_2.setBounds(310, 42, 58, 21);
		panel_1.add(lbl_dName_3_2);
		
		JLabel lbl_dName_3 = new JLabel("Ad Soyad");
		lbl_dName_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3.setBounds(396, 42, 82, 21);
		panel_1.add(lbl_dName_3);
		
		textField = new JTextField();
		textField.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(396, 63, 166, 30);
		panel_1.add(textField);
		
		JLabel lbl_dTC_1 = new JLabel("TC No");
		lbl_dTC_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dTC_1.setBounds(396, 98, 82, 21);
		panel_1.add(lbl_dTC_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(396, 115, 166, 30);
		panel_1.add(textField_1);
		
		JLabel lbl_dPass_1 = new JLabel("\u015Eifre");
		lbl_dPass_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dPass_1.setBounds(396, 150, 82, 21);
		panel_1.add(lbl_dPass_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_2.setColumns(10);
		textField_2.setBounds(396, 168, 166, 30);
		panel_1.add(textField_2);
		
		JLabel lbl_dName_1_1 = new JLabel("Telefon");
		lbl_dName_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_1_1.setBounds(396, 201, 82, 21);
		panel_1.add(lbl_dName_1_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_3.setColumns(10);
		textField_3.setBounds(396, 222, 166, 30);
		panel_1.add(textField_3);
		
		JLabel lbl_dName_2_1 = new JLabel("Adres");
		lbl_dName_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_2_1.setBounds(396, 252, 82, 21);
		panel_1.add(lbl_dName_2_1);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_4.setColumns(10);
		textField_4.setBounds(396, 270, 166, 46);
		panel_1.add(textField_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 204, 255));
		panel_2.setBounds(378, 39, 199, 290);
		panel_1.add(panel_2);
		
		JButton btn_dDelete_1 = new JButton(new ImageIcon(getClass().getResource("/save.png")));
		btn_dDelete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() == 0 || textField_1.getText().length() == 0
						|| textField_2.getText().length() == 0) {
					Helper.showMsg("complete");
				}
				else if(textField_1.getText().length() !=11 ){
					Helper.showMsg("TC kimlik numarasý 11 haneli olmalýdýr!");
				}
				else if(textField_2.getText().length() !=6) {
					Helper.showMsg("Þifre 6 haneli olmalýdýr!");
				}else {
					try {
						int selectID = patient.getId();
						boolean control = patient.updatePatient(selectID,textField_1.getText(), textField_2.getText(),
								textField.getText(),textField_3.getText(),textField_4.getText());
						if (control) {
							Helper.showMsg("success");
							textField.setText(null);
							textField_1.setText(null);
							textField_2.setText(null);
							textField_3.setText(null);
							textField_4.setText(null);	
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}				
				}
							
			}
		});
		btn_dDelete_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_dDelete_1.setBackground(Color.LIGHT_GRAY);
		btn_dDelete_1.setBounds(592, 283, 58, 46);
		panel_1.add(btn_dDelete_1);
		
		JLabel lbl_dName_3_2_1 = new JLabel("Kaydet");
		lbl_dName_3_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3_2_1.setBounds(592, 262, 70, 21);
		panel_1.add(lbl_dName_3_2_1);
		
		JButton btnNewButton = new JButton(new ImageIcon(getClass().getResource("/help.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Helper.helpMsg("patient");
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(628, 13, 38, 35);
		contentPane.add(btnNewButton);
	}

	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < whour.getWorkHourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWorkHourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWorkHourList(doctor_id).get(i).getDoctor_name();
			whourData[2] = whour.getWorkHourList(doctor_id).get(i).getWork_date();
			whourModel.addRow(whourData);
		}
	}

	//Polymorphism:Ürettiðim appoint nesnesini bu methodda farklý degerler atayarak randevularý hasta ýd'sine göre listeletebildik.
	public void updateAppointModel(int patientID) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getPatientList(patientID).size(); i++) {
			appointData[0] = appoint.getPatientList(patientID).get(i).getId();
			appointData[1] = appoint.getPatientList(patientID).get(i).getDoctorName();
			appointData[2] = appoint.getPatientList(patientID).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

	}
}