package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import Helper.*;
import javax.swing.JComboBox;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JTextPane;

public class ManagerGUI extends JFrame {
	public static Manager bashekim = new Manager();// Polymorphism: farklý nesneler üreterek bunlarý manager class'ýnda
													// farklý þekilde kullandým.
	private static Doctor doctor = new Doctor();
	public static Polyclinic polyclinic = new Polyclinic();
	private static Appointment appoint = new Appointment();
	private static Patient patient = new Patient();
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_dTc;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTextField fld_dPass;
	private JTable table_polyclinic;
	private JTextField fld_polyName;
	private DefaultTableModel polyclinicModel = null;
	private Object[] polyclinicData = null;
	private JPopupMenu doctorMenu;
	private JPopupMenu polyclinicMenu;
	private JTable table_employee;
	private JTable table;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private JTextField fld_search;
	private JTextField fld_search_1;
	private JTextField fld_telephone;
	private JTextField fld_address;
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
					ManagerGUI frame = new ManagerGUI(bashekim);
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
	public ManagerGUI(Manager bashekim) throws SQLException {
		setTitle("Hastane Y\u00F6netim Sistemi");

		// Doktor model
		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[6];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		colDoctor[2] = "TC No";
		colDoctor[3] = "Þifre";
		colDoctor[4] = "Telefon";
		colDoctor[5] = "Adres";

		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[6];
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorData[4] = bashekim.getDoctorList().get(i).getTelephone();
			doctorData[5] = bashekim.getDoctorList().get(i).getAddress();
			doctorModel.addRow(doctorData);
		}

		// Poliklinik model
		polyclinicModel = new DefaultTableModel();
		Object[] colPolyclinic = new Object[2];
		colPolyclinic[0] = "ID";
		colPolyclinic[1] = "Ad Soyad";
		polyclinicModel.setColumnIdentifiers(colPolyclinic);
		polyclinicData = new Object[2];
		for (int i = 0; i < polyclinic.getList().size(); i++) {
			polyclinicData[0] = polyclinic.getList().get(i).getId();
			polyclinicData[1] = polyclinic.getList().get(i).getName();
			polyclinicModel.addRow(polyclinicData);
		}

		// employee model
		DefaultTableModel employeeModel = new DefaultTableModel();
		Object[] colEmployee = new Object[2];
		colEmployee[0] = "ID";
		colEmployee[1] = "Ad Soyad";
		employeeModel.setColumnIdentifiers(colEmployee);
		Object[] employeeData = new Object[2];

		// randevu model
		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[4];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doctor Adý";
		colAppoint[2] = "Hasta Adý";
		colAppoint[3] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[4];

		for (int i = 0; i < appoint.getManagerAppointmentList().size(); i++) {
			appointData[0] = appoint.getManagerAppointmentList().get(i).getId();
			appointData[1] = appoint.getManagerAppointmentList().get(i).getDoctorName();
			appointData[2] = appoint.getManagerAppointmentList().get(i).getPatientName();
			appointData[3] = appoint.getManagerAppointmentList().get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setForeground(Color.BLACK);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_welcome = new JLabel("Hoþgeldiniz, Sayýn " + bashekim.getName());
		lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_welcome.setBounds(150, 32, 264, 42);
		w_pane.add(lbl_welcome);

		// Çýkýþ yap butonu
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
		w_pane.add(btn_exit);

		ImageIcon imageIcon = new ImageIcon("doctor.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(5, 5, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back

		JLabel lbl_managericon = new JLabel(new ImageIcon(getClass().getResource("/manager.png")));
		lbl_managericon.setBounds(10, 0, 135, 119);
		w_pane.add(lbl_managericon);

		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 120, 714, 380);
		w_pane.add(w_tabpane);

		// Doktor table için pop-up menu oluþturur.
		doctorMenu = new JPopupMenu();
		JMenuItem deleteMenu1 = new JMenuItem("Sil");
		doctorMenu.add(deleteMenu1);
		deleteMenu1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (Helper.confirm("sure")) {
						int selectedID = Integer
								.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
						if (bashekim.deleteDoctor(selectedID)) {
							Helper.showMsg("success");
							updateDoctorModel();
						} else {
							Helper.showMsg("error");
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		JPanel w_doctor = new JPanel();
		w_doctor.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Ýþlemleri", null, w_doctor, null);
		w_doctor.setLayout(null);

		JLabel lbl_dName = new JLabel("Ad Soyad");
		lbl_dName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName.setBounds(533, 9, 82, 21);
		w_doctor.add(lbl_dName);

		fld_dName = new JTextField();
		fld_dName.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_dName.setColumns(10);
		fld_dName.setBounds(533, 30, 166, 30);
		w_doctor.add(fld_dName);

		fld_dTc = new JTextField();
		fld_dTc.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_dTc.setColumns(10);
		fld_dTc.setBounds(533, 82, 166, 30);
		w_doctor.add(fld_dTc);

		JLabel lbl_dTC = new JLabel("TC No");
		lbl_dTC.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dTC.setBounds(533, 65, 82, 21);
		w_doctor.add(lbl_dTC);

		JLabel lbl_dPass = new JLabel("\u015Eifre");
		lbl_dPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dPass.setBounds(533, 117, 82, 21);
		w_doctor.add(lbl_dPass);

		// Doktor ekleme butonu.Bu methodda doktor ekler.
		JButton btn_add = new JButton(new ImageIcon(getClass().getResource("/adddoctor.png")));
		btn_add.setBackground(Color.LIGHT_GRAY);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dTc.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_dName.getText().length() == 0) {
					Helper.showMsg("complete");
				} else if (fld_dTc.getText().length() != 11) {
					Helper.showMsg("TC kimlik numarasý 11 haneli olmalýdýr!");
				} else if (fld_dPass.getText().length() != 6) {
					Helper.showMsg("Þifre 6 haneli olmalýdýr!");
				} else {
					try {
						boolean control = bashekim.addDoctor(fld_dTc.getText(), fld_dPass.getText(),
								fld_dName.getText(), fld_telephone.getText(), fld_address.getText());
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null);
							fld_dTc.setText(null);
							fld_dPass.setText(null);
							fld_telephone.setText(null);
							fld_address.setText(null);
							updateDoctorModel();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_add.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_add.setBounds(532, 287, 53, 54);
		w_doctor.add(btn_add);

		JButton btn_dDelete = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
		btn_dDelete.setBackground(Color.LIGHT_GRAY);

		// Doktor silme butonu.Bu methodda doktor siler.
		btn_dDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table_doctor.getSelectedRow();
				if (selectRow >= 0) {
					try {
						if (Helper.confirm("sure")) {
							String select = table_doctor.getModel().getValueAt(selectRow, 0).toString();
							int selectID = Integer.parseInt(select);
							boolean control = bashekim.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								updateDoctorModel();
							}
						} else {
							Helper.showMsg("cancel");
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz!");
				}
			}
		});

		btn_dDelete.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_dDelete.setBounds(646, 287, 53, 54);
		w_doctor.add(btn_dDelete);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 43, 519, 298);
		w_doctor.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		table_doctor.setComponentPopupMenu(doctorMenu);
		// Tabloda seçilen doktorun bilgilerini text_field'lere yazdýrýr.
		table_doctor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fld_dName.setText((String) doctorModel.getValueAt(table_doctor.getSelectedRow(), 1));
				fld_dTc.setText((String) doctorModel.getValueAt(table_doctor.getSelectedRow(), 2));
				fld_dPass.setText((String) doctorModel.getValueAt(table_doctor.getSelectedRow(), 3));
				fld_telephone.setText((String) doctorModel.getValueAt(table_doctor.getSelectedRow(), 4));
				fld_address.setText((String) doctorModel.getValueAt(table_doctor.getSelectedRow(), 5));
			}
		});
		w_scrollDoctor.setViewportView(table_doctor);

		fld_dPass = new JTextField();
		fld_dPass.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_dPass.setColumns(10);
		fld_dPass.setBounds(533, 135, 166, 30);
		w_doctor.add(fld_dPass);

		// Doktor bilgilerini günceller.
		JButton btn_dUpdate = new JButton(new ImageIcon(getClass().getResource("/update.png")));
		btn_dUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dTc.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_dName.getText().length() == 0) {
					Helper.showMsg("complete");
				} else if (fld_dTc.getText().length() != 11) {
					Helper.showMsg("TC kimlik numarasý 11 haneli olmalýdýr!");
				} else if (fld_dPass.getText().length() != 6) {
					Helper.showMsg("Þifre 6 haneli olmalýdýr!");
				} else {
					try {
						int selectID = Integer
								.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
						boolean control = doctor.updateDoctor(selectID, fld_dTc.getText(), fld_dPass.getText(),
								fld_dName.getText(), fld_telephone.getText(), fld_address.getText());
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null);
							fld_dTc.setText(null);
							fld_dPass.setText(null);
							fld_telephone.setText(null);
							fld_address.setText(null);
							updateDoctorModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_dUpdate.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_dUpdate.setBackground(Color.LIGHT_GRAY);
		btn_dUpdate.setBounds(589, 287, 53, 54);
		w_doctor.add(btn_dUpdate);

		JLabel lbl_dsearch = new JLabel("Arama");
		lbl_dsearch.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_dsearch.setBounds(10, 9, 53, 21);
		w_doctor.add(lbl_dsearch);

		fld_search = new JTextField();
		fld_search.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_search.setColumns(10);
		fld_search.setBounds(65, 6, 226, 30);
		w_doctor.add(fld_search);

		// Doktor arar
		JButton btn_search = new JButton("Ara");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
				clearModel.setRowCount(0);
				String name = fld_search.getText();
				try {
					for (int i = 0; i < bashekim.getDoctorSearch(name).size(); i++) {
						doctorData[0] = bashekim.getDoctorSearch(name).get(i).getId();
						doctorData[1] = bashekim.getDoctorSearch(name).get(i).getName();
						doctorData[2] = bashekim.getDoctorSearch(name).get(i).getTcno();
						doctorData[3] = bashekim.getDoctorSearch(name).get(i).getPassword();
						doctorData[4] = bashekim.getDoctorSearch(name).get(i).getTelephone();
						doctorData[5] = bashekim.getDoctorSearch(name).get(i).getAddress();
						doctorModel.addRow(doctorData);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btn_search.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search.setBounds(303, 7, 112, 30);
		w_doctor.add(btn_search);

		// Aramayý temizler.Tabloyu tüm liste yapar.
		JButton btn_search_1 = new JButton("Sil");
		btn_search_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fld_search.setText(null);
				try {
					updateDoctorModel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_search_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_1.setBounds(421, 7, 105, 30);
		w_doctor.add(btn_search_1);

		JLabel lbl_dName_1 = new JLabel("Telefon");
		lbl_dName_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_1.setBounds(533, 168, 82, 21);
		w_doctor.add(lbl_dName_1);

		fld_telephone = new JTextField();
		fld_telephone.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_telephone.setColumns(10);
		fld_telephone.setBounds(533, 189, 166, 30);
		w_doctor.add(fld_telephone);

		JLabel lbl_dName_2 = new JLabel("Adres");
		lbl_dName_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_2.setBounds(533, 219, 82, 21);
		w_doctor.add(lbl_dName_2);

		fld_address = new JTextField();
		fld_address.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_address.setColumns(10);
		fld_address.setBounds(533, 237, 166, 46);
		w_doctor.add(fld_address);

		JPanel w_polyclinic = new JPanel();
		w_polyclinic.setBackground(Color.WHITE);
		w_tabpane.addTab("Poliklinik Ýþlemleri", null, w_polyclinic, null);
		w_polyclinic.setLayout(null);

		JScrollPane w_scrollPolyclinic = new JScrollPane();
		w_scrollPolyclinic.setBounds(10, 28, 256, 313);
		w_polyclinic.add(w_scrollPolyclinic);

		table_polyclinic = new JTable(polyclinicModel);
		// Tabloda seçilen doktorun bilgilerini text_field'lere yazdýrýr.
		table_polyclinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fld_polyName.setText((String) polyclinicModel.getValueAt(table_polyclinic.getSelectedRow(), 1));
			}
		});

		w_scrollPolyclinic.setViewportView(table_polyclinic);

		JScrollPane w_scrollEmployee = new JScrollPane();
		w_scrollEmployee.setBounds(443, 28, 256, 313);
		w_polyclinic.add(w_scrollEmployee);

		table_employee = new JTable();
		w_scrollEmployee.setViewportView(table_employee);

		// Poliklinik günceller
		JButton btn_polyUpdate = new JButton("Düzenle");
		btn_polyUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_polyName.getText().length() == 0) {
					Helper.showMsg("complete");
				} else {
					int selectID = Integer
							.parseInt(table_polyclinic.getValueAt(table_polyclinic.getSelectedRow(), 0).toString());
					try {
						boolean control = polyclinic.updatePolyclinic(selectID, fld_polyName.getText());
						fld_polyName.setText(null);
						updatePolyclinicModel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		JLabel lbl_policilinicName = new JLabel("Poliklinik Ad\u0131");
		lbl_policilinicName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_policilinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_policilinicName.setBounds(277, 11, 156, 21);
		w_polyclinic.add(lbl_policilinicName);

		fld_polyName = new JTextField();
		fld_polyName.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_polyName.setColumns(10);
		fld_polyName.setBounds(276, 28, 156, 30);
		w_polyclinic.add(fld_polyName);

		// poliklinik ekleme butonu.
		JButton btn_addPolicilinic = new JButton("Ekle");
		btn_addPolicilinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_polyName.getText().length() == 0) {
					Helper.showMsg("complate");
				} else {
					try {
						if (polyclinic.addPolyclinic(fld_polyName.getText())) {
							Helper.showMsg("success");
							fld_polyName.setText(null);
							updatePolyclinicModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btn_addPolicilinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addPolicilinic.setBounds(276, 61, 156, 30);
		w_polyclinic.add(btn_addPolicilinic);

		JComboBox combo_doctor = new JComboBox();
		combo_doctor.setToolTipText("");
		combo_doctor.setMaximumRowCount(100);
		combo_doctor.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		combo_doctor.setBounds(276, 244, 156, 30);

		// Combobox'a doktorlarý listeler
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			combo_doctor.addItem(
					new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
		}
		combo_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : " + item.getValue());
		});
		w_polyclinic.add(combo_doctor);

		JButton btn_addEmployee = new JButton(new ImageIcon(getClass().getResource("/choosedoctor.png")));
		btn_addEmployee.setBackground(new Color(255, 255, 224));

		// Ýþçi ekleme butonu.Bu methodda iþçi ekler.
		btn_addEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table_polyclinic.getSelectedRow();
				if (selectRow >= 0) {
					String selPolyclinic = table_polyclinic.getModel().getValueAt(selectRow, 0).toString();
					int selPolyclinicID = Integer.parseInt(selPolyclinic);
					Item doctorItem = (Item) combo_doctor.getSelectedItem();
					try {
						boolean control = bashekim.addEmployee(doctorItem.getKey(), selPolyclinicID);
						if (control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) table_employee.getModel();
							clearModel.setRowCount(0);

							for (int i = 0; i < bashekim.getPolyclinicDoctorList(selPolyclinicID).size(); i++) {
								employeeData[0] = bashekim.getPolyclinicDoctorList(selPolyclinicID).get(i).getId();
								employeeData[1] = bashekim.getPolyclinicDoctorList(selPolyclinicID).get(i).getName();
								employeeModel.addRow(employeeData);
							}

						} else {
							Helper.showMsg("error");
						}
						table_employee.setModel(employeeModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else {
					Helper.showMsg("Lütfen bir polikilinik seçiniz!");
				}
			}
		});

		btn_addEmployee.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addEmployee.setBounds(305, 275, 97, 66);
		w_polyclinic.add(btn_addEmployee);
		JLabel lbl_policName = new JLabel("Poliklinik Ad\u0131");
		lbl_policName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_policName.setBounds(276, 169, 106, 21);
		w_polyclinic.add(lbl_policName);

		JButton btn_polySelect = new JButton("Se\u00E7");
		btn_polySelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table_polyclinic.getSelectedRow();
				if (selectRow >= 0) {
					String selPolyclinic = table_polyclinic.getModel().getValueAt(selectRow, 0).toString();
					int selPolyclinicID = Integer.parseInt(selPolyclinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_employee.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < bashekim.getPolyclinicDoctorList(selPolyclinicID).size(); i++) {
							employeeData[0] = bashekim.getPolyclinicDoctorList(selPolyclinicID).get(i).getId();
							employeeData[1] = bashekim.getPolyclinicDoctorList(selPolyclinicID).get(i).getName();
							employeeModel.addRow(employeeData);
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_employee.setModel(employeeModel);

				} else {
					Helper.showMsg("Lütfen bir polikilinik seçiniz!");
				}
			}
		});
		btn_polySelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_polySelect.setBounds(276, 190, 156, 30);
		w_polyclinic.add(btn_polySelect);

		JLabel lbl_docChoose = new JLabel("Doktor Se\u00E7");
		lbl_docChoose.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_docChoose.setBounds(276, 219, 106, 21);
		w_polyclinic.add(lbl_docChoose);

		btn_polyUpdate.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_polyUpdate.setBounds(276, 96, 157, 30);
		w_polyclinic.add(btn_polyUpdate);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 204, 153));
		panel_2.setBounds(271, 11, 168, 156);
		w_polyclinic.add(panel_2);
		panel_2.setLayout(null);

		JButton btn_polyUpdate_1 = new JButton("Sil");
		btn_polyUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table_polyclinic.getSelectedRow();
				if (selectRow >= 0) {
					try {
						if (Helper.confirm("sure")) {
							String select = table_polyclinic.getModel().getValueAt(selectRow, 0).toString();
							int selectID = Integer.parseInt(select);
							boolean control = bashekim.deletePolyclinic(selectID);
							if (control) {
								Helper.showMsg("success");
								fld_polyName.setText(null);
								updatePolyclinicModel();
							}
						} else {
							Helper.showMsg("cancel");
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz!");
				}
			}

		});
		btn_polyUpdate_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_polyUpdate_1.setBounds(6, 120, 157, 30);
		panel_2.add(btn_polyUpdate_1);

		JLabel lbl_pNamee = new JLabel("Poliklinik Listesi");
		lbl_pNamee.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_pNamee.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_pNamee.setBounds(10, 9, 256, 21);
		w_polyclinic.add(lbl_pNamee);

		JLabel lbl_dNamee = new JLabel("Poliklinikteki Doktorlar\u0131n Listesi");
		lbl_dNamee.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_dNamee.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_dNamee.setBounds(443, 9, 256, 21);
		w_polyclinic.add(lbl_dNamee);

		JPanel panel_appdelete = new JPanel();
		panel_appdelete.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Randevularý", null, panel_appdelete, null);
		panel_appdelete.setLayout(null);

		JScrollPane scroll_apppoint = new JScrollPane();
		scroll_apppoint.setBounds(10, 48, 689, 293);
		panel_appdelete.add(scroll_apppoint);

		table = new JTable(appointModel);
		scroll_apppoint.setViewportView(table);

		JButton btn_delete = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = table.getSelectedRow();
				if (selectRow >= 0) {
					int doctorID = 0;
					try {
						doctorID = appoint.getManagerAppointmentList().get(0).getDoctorID();
						if (Helper.confirm("sure")) {
							String seldate = table.getModel().getValueAt(selectRow, 2).toString();
							String select = table.getModel().getValueAt(selectRow, 0).toString();
							int selectID = Integer.parseInt(select);
							boolean control = patient.deleteAppointment(selectID);
							if (control) {
								Helper.showMsg("success");
								patient.updateWStatus(doctorID, seldate);
								updateAppointModel();
								// updateWhourModel(doctorID);
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
		btn_delete.setBounds(650, 11, 49, 35);
		panel_appdelete.add(btn_delete);

		JLabel lbl_dsearch_1 = new JLabel("Arama");
		lbl_dsearch_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lbl_dsearch_1.setBounds(10, 14, 53, 21);
		panel_appdelete.add(lbl_dsearch_1);

		fld_search_1 = new JTextField();
		fld_search_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_search_1.setColumns(10);
		fld_search_1.setBounds(65, 11, 226, 30);
		panel_appdelete.add(fld_search_1);

		JButton btn_search_2 = new JButton("Ara");
		btn_search_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
				clearModel.setRowCount(0);
				String name = fld_search_1.getText();
				try {
					for (int i = 0; i < appoint.getManagerAppointmentSearchList(name).size(); i++) {
						appointData[0] = appoint.getManagerAppointmentSearchList(name).get(i).getId();
						appointData[1] = appoint.getManagerAppointmentSearchList(name).get(i).getDoctorName();
						appointData[2] = appoint.getManagerAppointmentSearchList(name).get(i).getPatientName();
						appointData[3] = appoint.getManagerAppointmentSearchList(name).get(i).getAppDate();
						appointModel.addRow(appointData);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_search_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_2.setBounds(303, 12, 112, 30);
		panel_appdelete.add(btn_search_2);

		JButton btn_search_1_1 = new JButton("Sil");
		btn_search_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fld_search_1.setText(null);
				try {
					updateAppointModel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_search_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_search_1_1.setBounds(421, 12, 112, 30);
		panel_appdelete.add(btn_search_1_1);

		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		panel_1.setBackground(Color.WHITE);
		w_tabpane.addTab("Bilgilerim", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel lbl_dName_3 = new JLabel("Ad Soyad");
		lbl_dName_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3.setBounds(397, 40, 82, 21);
		panel_1.add(lbl_dName_3);

		textField = new JTextField();
		textField.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(397, 61, 166, 30);
		panel_1.add(textField);

		JLabel lbl_dTC_1 = new JLabel("TC No");
		lbl_dTC_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dTC_1.setBounds(397, 96, 82, 21);
		panel_1.add(lbl_dTC_1);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(397, 113, 166, 30);
		panel_1.add(textField_1);

		JLabel lbl_dPass_1 = new JLabel("\u015Eifre");
		lbl_dPass_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dPass_1.setBounds(397, 148, 82, 21);
		panel_1.add(lbl_dPass_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_2.setColumns(10);
		textField_2.setBounds(397, 166, 166, 30);
		panel_1.add(textField_2);

		JLabel lbl_dName_1_1 = new JLabel("Telefon");
		lbl_dName_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_1_1.setBounds(397, 199, 82, 21);
		panel_1.add(lbl_dName_1_1);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_3.setColumns(10);
		textField_3.setBounds(397, 220, 166, 30);
		panel_1.add(textField_3);

		JLabel lbl_dName_2_1 = new JLabel("Adres");
		lbl_dName_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_2_1.setBounds(397, 250, 82, 21);
		panel_1.add(lbl_dName_2_1);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		textField_4.setColumns(10);
		textField_4.setBounds(397, 268, 166, 46);
		panel_1.add(textField_4);

		JButton btn_dUpdate_1 = new JButton(new ImageIcon(getClass().getResource("/arrangement.png")));
		btn_dUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(bashekim.getName());
				textField_1.setText(bashekim.getTcno());
				textField_2.setText(bashekim.getPassword());
				textField_3.setText(bashekim.getTelephone());
				textField_4.setText(bashekim.getAddress());

			}
		});
		btn_dUpdate_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_dUpdate_1.setBackground(Color.LIGHT_GRAY);
		btn_dUpdate_1.setBounds(311, 61, 58, 46);
		panel_1.add(btn_dUpdate_1);

		JButton btn_dDelete_1 = new JButton(new ImageIcon(getClass().getResource("/save.png")));
		btn_dDelete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() == 0 || textField_1.getText().length() == 0
						|| textField_2.getText().length() == 0) {
					Helper.showMsg("complete");
				} else if (textField_1.getText().length() != 11) {
					Helper.showMsg("TC kimlik numarasý 11 haneli olmalýdýr!");
				} else if (textField_2.getText().length() != 6) {
					Helper.showMsg("Þifre 6 haneli olmalýdýr!");
				} else {
					try {
						int selectID = bashekim.getId();
						boolean control = doctor.updateDoctor(selectID, textField_1.getText(), textField_2.getText(),
								textField.getText(), textField_3.getText(), textField_4.getText());
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
		btn_dDelete_1.setBounds(593, 281, 58, 46);
		panel_1.add(btn_dDelete_1);

		JLabel lbl_dName_3_1 = new JLabel("Bilgilerim:");
		lbl_dName_3_1.setFont(new Font("Impact", Font.PLAIN, 17));
		lbl_dName_3_1.setBounds(47, 9, 82, 21);
		panel_1.add(lbl_dName_3_1);

		JLabel lblNewLabel = new JLabel("Ad Soyad: " + bashekim.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel.setBackground(new Color(204, 204, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(47, 32, 230, 35);
		panel_1.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 204, 255));
		panel.setBounds(379, 37, 199, 290);
		panel_1.add(panel);

		JLabel lbl_dName_3_2 = new JLabel("D\u00FCzenle");
		lbl_dName_3_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3_2.setBounds(311, 40, 58, 21);
		panel_1.add(lbl_dName_3_2);

		JLabel lbl_dName_3_2_1 = new JLabel("Kaydet");
		lbl_dName_3_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_dName_3_2_1.setBounds(593, 260, 70, 21);
		panel_1.add(lbl_dName_3_2_1);

		JLabel lblNewLabel_1 = new JLabel("Tc: " + bashekim.getTcno());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_1.setBackground(new Color(204, 204, 255));
		lblNewLabel_1.setBounds(47, 73, 254, 35);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Þifre: " + bashekim.getPassword());
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_2.setBackground(new Color(204, 204, 255));
		lblNewLabel_2.setBounds(47, 113, 254, 35);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Telefon: " + bashekim.getTelephone());
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_3.setBackground(new Color(204, 204, 255));
		lblNewLabel_3.setBounds(47, 152, 254, 35);
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Adres: " + bashekim.getAddress());
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblNewLabel_4.setBackground(new Color(204, 204, 255));
		lblNewLabel_4.setBounds(47, 188, 254, 35);
		panel_1.add(lblNewLabel_4);

		JPanel panel_3 = new JPanel();
		panel_3.setToolTipText("");
		panel_3.setBackground(new Color(255, 255, 153));
		panel_3.setBounds(46, 32, 255, 206);
		panel_1.add(panel_3);

		JButton btn_exit_1 = new JButton(new ImageIcon(getClass().getResource("/help.png")));
		btn_exit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Helper.helpMsg("manager");
			}
		});
		btn_exit_1.setBackground(Color.WHITE);
		btn_exit_1.setBounds(624, 11, 45, 33);
		w_pane.add(btn_exit_1);
	}

	// Doktor tablosunu günceller. Son eklenene ya da silinene göre düzenler.
	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorData[4] = bashekim.getDoctorList().get(i).getTelephone();
			doctorData[5] = bashekim.getDoctorList().get(i).getAddress();
			doctorModel.addRow(doctorData);
		}
	}

	// Poliklinic tablosunu günceller. Son eklenene ya da silinene göre düzenler
	public void updatePolyclinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_polyclinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < polyclinic.getList().size(); i++) {
			polyclinicData[0] = polyclinic.getList().get(i).getId();
			polyclinicData[1] = polyclinic.getList().get(i).getName();
			polyclinicModel.addRow(polyclinicData);
		}
	}

	public void updateAppointModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < appoint.getManagerAppointmentList().size(); i++) {
			appointData[0] = appoint.getManagerAppointmentList().get(i).getId();
			appointData[1] = appoint.getManagerAppointmentList().get(i).getDoctorName();
			appointData[2] = appoint.getManagerAppointmentList().get(i).getPatientName();
			appointData[3] = appoint.getManagerAppointmentList().get(i).getAppDate();
			appointModel.addRow(appointData);
		}

	}
}
