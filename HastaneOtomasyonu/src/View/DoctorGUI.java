package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Model.Appointment;
import Model.Doctor;
import Model.Patient;
import Model.WorkHour;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import com.toedter.calendar.JDayChooser;

import Helper.Helper;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DoctorGUI extends JFrame {

	private static Doctor doctor = new Doctor();
	private static WorkHour whour = new WorkHour();
	private static Patient patient = new Patient();
	private static Appointment appoint = new Appointment();
	private JPanel contentPane;
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private JTable table;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
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
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) throws SQLException {

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		for (int i = 0; i < whour.getWorkHourList(doctor.getId()).size(); i++) {
			whourData[0] = whour.getWorkHourList(doctor.getId()).get(i).getId();
			whourData[1] = whour.getWorkHourList(doctor.getId()).get(i).getWork_date();
			whourModel.addRow(whourData);
		}

		// randevu model
		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doctor Adý";
		colAppoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];

		for (int i = 0; i < appoint.getDoctorAppointmentList(doctor.getId()).size(); i++) {
			appointData[0] = appoint.getDoctorAppointmentList(doctor.getId()).get(i).getId();
			appointData[1] = appoint.getDoctorAppointmentList(doctor.getId()).get(i).getPatientName();
			appointData[2] = appoint.getDoctorAppointmentList(doctor.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_doktoricon = new JLabel(new ImageIcon(getClass().getResource("/doctor.png")));
		lbl_doktoricon.setBounds(10, 11, 135, 109);
		contentPane.add(lbl_doktoricon);

		JTabbedPane w_whourPane = new JTabbedPane(JTabbedPane.TOP);
		w_whourPane.setBounds(10, 120, 714, 380);
		contentPane.add(w_whourPane);

		JPanel panel_whour = new JPanel();
		panel_whour.setBackground(Color.WHITE);
		w_whourPane.addTab("Randevu Oluþtur", null, panel_whour, null);
		panel_whour.setLayout(null);

		JDateChooser select_wdate = new JDateChooser();
		select_wdate.getCalendarButton().setBackground(new Color(135, 206, 235));
		select_wdate.getCalendarButton().setForeground(new Color(0, 0, 0));
		select_wdate.setBounds(558, 70, 138, 35);
		panel_whour.add(select_wdate);

		JComboBox combo_whour = new JComboBox();
		combo_whour.setFont(new Font("Yu Gothic UI", Font.PLAIN, 13));
		combo_whour.setModel(new DefaultComboBoxModel(new String[] { "9:00", "9:30", "10:00", "10:30", "11:00", "11:30",
				"12:00", "12:30", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "" }));
		combo_whour.setBounds(564, 116, 132, 35);
		panel_whour.add(combo_whour);

		JButton btn_addWhour = new JButton(new ImageIcon(getClass().getResource("/adddate.png")));
		btn_addWhour.setBackground(new Color(255, 255, 224));
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_wdate.getDate());
				} catch (Exception e2) {
					// TOOO: handle exception
				}
				if (date.length() == 0) {
					Helper.showMsg("Lüften geçerli bir tarih þeçiniz!");
				} else {

					String hour = " " + combo_whour.getSelectedItem().toString() + ":00";
					String selectDate = date + hour;
					try {
						boolean control = doctor.addWorkHour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addWhour.setBounds(599, 161, 65, 47);
		panel_whour.add(btn_addWhour);

		JScrollPane scroll_whour = new JScrollPane();
		scroll_whour.setBounds(10, 46, 538, 295);
		panel_whour.add(scroll_whour);

		table_whour = new JTable(whourModel);
		scroll_whour.setViewportView(table_whour);

		JButton btn_deleteWhour = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
		btn_deleteWhour.setBackground(new Color(255, 255, 224));
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table_whour.getSelectedRow();
				if (selectRow >= 0) {
					String select = table_whour.getModel().getValueAt(selectRow, 0).toString();
					int selectID = Integer.parseInt(select);
					boolean control;
					try {
						control = doctor.deleteWorkHour(selectID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);

						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		btn_deleteWhour.setBounds(599, 263, 65, 47);
		panel_whour.add(btn_deleteWhour);

		JLabel lbl_dsearch_1 = new JLabel("Arama");
		lbl_dsearch_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_dsearch_1.setBounds(10, 14, 53, 21);
		panel_whour.add(lbl_dsearch_1);

		JDateChooser select_wdate_1 = new JDateChooser();
		select_wdate_1.getCalendarButton().setForeground(Color.BLACK);
		select_wdate_1.getCalendarButton().setBackground(new Color(135, 206, 235));
		select_wdate_1.setBounds(65, 11, 226, 30);
		panel_whour.add(select_wdate_1);

		JButton btn_search_2 = new JButton("Ara");
		btn_search_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_wdate_1.getDate());
				} catch (Exception e2) {
					// TOOO: handle exception
				}
				if (date.length() == 0) {
					Helper.showMsg("Lüften geçerli bir tarih þeçiniz!");
				} else {
					String selectDate = date; // + hour;
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);
					int doctorID = 0;
					try {
						doctorID = appoint.getDoctorAppointmentList(doctor.getId()).get(0).getDoctorID();
						for (int i = 0; i < whour.getWorkHourSearchList(doctorID, selectDate).size(); i++) {
							whourData[0] = whour.getWorkHourSearchList(doctorID, selectDate).get(i).getId();
							whourData[1] = whour.getWorkHourSearchList(doctorID, selectDate).get(i).getWork_date();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_search_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_2.setBounds(301, 9, 110, 30);
		panel_whour.add(btn_search_2);

		JButton btn_search_1_1 = new JButton("Sil");
		btn_search_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int doctorID = appoint.getDoctorAppointmentList(doctor.getId()).get(0).getDoctorID();
					updateWhourrModel(doctorID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_search_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_1_1.setBounds(421, 9, 112, 30);
		panel_whour.add(btn_search_1_1);

		JLabel lbl_dsearch_1_1 = new JLabel("Randevu Olu\u015Ftur");
		lbl_dsearch_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_dsearch_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_dsearch_1_1.setBounds(558, 46, 138, 21);
		panel_whour.add(lbl_dsearch_1_1);

		JLabel lbl_dsearch_1_2 = new JLabel("Sil");
		lbl_dsearch_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_dsearch_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_dsearch_1_2.setBounds(599, 239, 53, 21);
		panel_whour.add(lbl_dsearch_1_2);

		JPanel panel_fullAppoint = new JPanel();
		panel_fullAppoint.setBackground(Color.WHITE);
		w_whourPane.addTab("Dolu Randevular", null, panel_fullAppoint, null);
		panel_fullAppoint.setLayout(null);

		JScrollPane scroll_apppoint = new JScrollPane();
		scroll_apppoint.setBounds(10, 49, 689, 292);
		panel_fullAppoint.add(scroll_apppoint);

		table = new JTable(appointModel);
		scroll_apppoint.setViewportView(table);

		JButton btn_delete = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
		btn_delete.setBackground(new Color(255, 255, 224));
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table.getSelectedRow();
				if (selectRow >= 0) {
					int doctorID = 0;
					try {
						doctorID = appoint.getDoctorAppointmentList(doctor.getId()).get(0).getDoctorID();
						if (Helper.confirm("sure")) {
							String seldate = table.getModel().getValueAt(selectRow, 2).toString();
							String select = table.getModel().getValueAt(selectRow, 0).toString();
							int selectID = Integer.parseInt(select);
							boolean control = patient.deleteAppointment(selectID);
							if (control) {
								Helper.showMsg("success");
								patient.updateWStatus(doctorID, seldate);
								updateAppointModel(doctorID);
								updateWhourrModel(doctorID);
							}
						} else {
							Helper.showMsg("cancel");
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				} else {
					Helper.showMsg("Lütfen geçerli bir randevu seçiniz!");
				}
			}

		});
		btn_delete.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_delete.setBounds(651, 8, 48, 35);
		panel_fullAppoint.add(btn_delete);

		JLabel lbl_dsearch_1_3 = new JLabel("Arama");
		lbl_dsearch_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_dsearch_1_3.setBounds(10, 11, 53, 21);
		panel_fullAppoint.add(lbl_dsearch_1_3);

		JDateChooser select_wdate_2 = new JDateChooser();
		select_wdate_2.getCalendarButton().setForeground(Color.BLACK);
		select_wdate_2.getCalendarButton().setBackground(new Color(135, 206, 235));
		select_wdate_2.setBounds(65, 8, 226, 30);
		panel_fullAppoint.add(select_wdate_2);

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
					int doctorID = 0;
					try {
						doctorID = appoint.getDoctorAppointmentList(doctor.getId()).get(0).getDoctorID();
						for (int i = 0; i < appoint.getDoctorAppointmentSearchList(doctorID, date).size(); i++) {
							appointData[0] = appoint.getDoctorAppointmentSearchList(doctorID, date).get(i).getId();
							appointData[1] = appoint.getDoctorAppointmentSearchList(doctorID, date).get(i)
									.getPatientName();
							appointData[2] = appoint.getDoctorAppointmentSearchList(doctorID, date).get(i).getAppDate();
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
		btn_search_2_1.setBounds(301, 8, 110, 30);
		panel_fullAppoint.add(btn_search_2_1);

		JButton btn_search_1_1_1 = new JButton("Sil");
		btn_search_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select_wdate_2.setDate(null);
				try {
					int doctorID = appoint.getDoctorAppointmentList(doctor.getId()).get(0).getDoctorID();
					updateAppointModel(doctorID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		btn_search_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_1_1_1.setBounds(421, 8, 112, 30);
		panel_fullAppoint.add(btn_search_1_1_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		w_whourPane.addTab("Bilgilerim", null, panel, null);
		panel.setLayout(null);
		
		JLabel lbl_dName_3_1 = new JLabel("Bilgilerim:");
		lbl_dName_3_1.setFont(new Font("Impact", Font.PLAIN, 17));
		lbl_dName_3_1.setBounds(36, 11, 82, 21);
		panel.add(lbl_dName_3_1);
		
		JLabel lblNewLabel = new JLabel("Ad Soyad: "+doctor.getName());
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel.setBackground(new Color(204, 204, 255));
		lblNewLabel.setBounds(36, 34, 230, 35);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tc: "+doctor.getTcno());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_1.setBackground(new Color(204, 204, 255));
		lblNewLabel_1.setBounds(36, 75, 254, 35);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u015Eifre: "+doctor.getPassword());
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_2.setBackground(new Color(204, 204, 255));
		lblNewLabel_2.setBounds(36, 115, 254, 35);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Telefon: "+doctor.getTelephone());
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_3.setBackground(new Color(204, 204, 255));
		lblNewLabel_3.setBounds(36, 154, 254, 35);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Adres: "+doctor.getAddress());
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_4.setBackground(new Color(204, 204, 255));
		lblNewLabel_4.setBounds(36, 190, 254, 35);
		panel.add(lblNewLabel_4);
		
		JPanel panel_3 = new JPanel();
		panel_3.setToolTipText("");
		panel_3.setBackground(new Color(255, 255, 153));
		panel_3.setBounds(35, 34, 255, 206);
		panel.add(panel_3);
		
		JButton btn_dUpdate_1 = new JButton(new ImageIcon(getClass().getResource("/arrangement.png")));
		btn_dUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(doctor.getName());
				textField_1.setText(doctor.getTcno());
				textField_2.setText(doctor.getPassword());
				textField_3.setText(doctor.getTelephone());
				textField_4.setText(doctor.getAddress());
				
			}
		});
		btn_dUpdate_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_dUpdate_1.setBackground(Color.LIGHT_GRAY);
		btn_dUpdate_1.setBounds(300, 63, 58, 46);
		panel.add(btn_dUpdate_1);
		
		JLabel lbl_dName_3_2 = new JLabel("D\u00FCzenle");
		lbl_dName_3_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3_2.setBounds(300, 42, 58, 21);
		panel.add(lbl_dName_3_2);
		
		JLabel lbl_dName_3 = new JLabel("Ad Soyad");
		lbl_dName_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3.setBounds(386, 42, 82, 21);
		panel.add(lbl_dName_3);
		
		textField = new JTextField();
		textField.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(386, 63, 166, 30);
		panel.add(textField);
		
		JLabel lbl_dTC_1 = new JLabel("TC No");
		lbl_dTC_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dTC_1.setBounds(386, 98, 82, 21);
		panel.add(lbl_dTC_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(386, 115, 166, 30);
		panel.add(textField_1);
		
		JLabel lbl_dPass_1 = new JLabel("\u015Eifre");
		lbl_dPass_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dPass_1.setBounds(386, 150, 82, 21);
		panel.add(lbl_dPass_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_2.setColumns(10);
		textField_2.setBounds(386, 168, 166, 30);
		panel.add(textField_2);
		
		JLabel lbl_dName_1_1 = new JLabel("Telefon");
		lbl_dName_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_1_1.setBounds(386, 201, 82, 21);
		panel.add(lbl_dName_1_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_3.setColumns(10);
		textField_3.setBounds(386, 222, 166, 30);
		panel.add(textField_3);
		
		JLabel lbl_dName_2_1 = new JLabel("Adres");
		lbl_dName_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_2_1.setBounds(386, 252, 82, 21);
		panel.add(lbl_dName_2_1);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_4.setColumns(10);
		textField_4.setBounds(386, 270, 166, 46);
		panel.add(textField_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 204, 255));
		panel_1.setBounds(368, 39, 199, 290);
		panel.add(panel_1);
		
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
						int selectID = doctor.getId();
						boolean control = doctor.updateDoctor(selectID,textField_1.getText(), textField_2.getText(),
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
		btn_dDelete_1.setBounds(582, 283, 58, 46);
		panel.add(btn_dDelete_1);
		
		JLabel lbl_dName_3_2_1 = new JLabel("Kaydet");
		lbl_dName_3_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3_2_1.setBounds(582, 262, 70, 21);
		panel.add(lbl_dName_3_2_1);

		JButton btn_exit = new JButton(new ImageIcon(getClass().getResource("/cancel.png")));
		btn_exit.setBackground(Color.WHITE);
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_exit.setBounds(679, 11, 45, 33);
		contentPane.add(btn_exit);

		JLabel lbl_welcome = new JLabel("Hoþgeldiniz, Sayýn " + doctor.getName());
		lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_welcome.setBounds(160, 32, 264, 42);
		contentPane.add(lbl_welcome);

		JButton btn_exit_1 = new JButton(new ImageIcon(getClass().getResource("/help.png")));
		btn_exit_1.setBackground(Color.WHITE);
		btn_exit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Helper.helpMsg("doctor");
			}
		});
		btn_exit_1.setBounds(626, 11, 45, 33);
		contentPane.add(btn_exit_1);
	}

	// Doktor tablosunu günceller. Son eklenene ya da silinene göre düzenler
	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWorkHourList(doctor.getId()).size(); i++) {
			whourData[0] = whour.getWorkHourList(doctor.getId()).get(i).getId();
			whourData[1] = whour.getWorkHourList(doctor.getId()).get(i).getWork_date();
			whourModel.addRow(whourData);
		}
	}

	public void updateWhourrModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < whour.getWorkHourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWorkHourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWorkHourList(doctor_id).get(i).getWork_date();
			whourModel.addRow(whourData);
		}
	}

	public void updateAppointModel(int doctorID) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < appoint.getDoctorAppointmentList(doctorID).size(); i++) {
			appointData[0] = appoint.getDoctorAppointmentList(doctorID).get(i).getId();
			appointData[1] = appoint.getDoctorAppointmentList(doctorID).get(i).getPatientName();
			appointData[2] = appoint.getDoctorAppointmentList(doctorID).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

	}
}