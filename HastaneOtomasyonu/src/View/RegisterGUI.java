package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Patient;
import Model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.SwingConstants;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fld_hName;
	private JTextField fld_hTC;
	private JPasswordField fld_hPass;
	private static User user = new User();
	private JTextField fld_telephone;
	private JTextField fld_address;
    static String type=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					RegisterGUI frame = new RegisterGUI(type);
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
	public RegisterGUI(String type) {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 262, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_hName = new JLabel("Ad Soyad *");
		lbl_hName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_hName.setBounds(33, 43, 72, 21);
		contentPane.add(lbl_hName);

		fld_hName = new JTextField();
		fld_hName.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_hName.setColumns(10);
		fld_hName.setBounds(33, 64, 175, 30);
		contentPane.add(fld_hName);

		JLabel lbl_hTC = new JLabel("TC No *");
		lbl_hTC.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_hTC.setBounds(33, 94, 72, 21);
		contentPane.add(lbl_hTC);

		fld_hTC = new JTextField();
		fld_hTC.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
		fld_hTC.setColumns(10);
		fld_hTC.setBounds(33, 113, 175, 30);
		contentPane.add(fld_hTC);

		JLabel lbl_hPass = new JLabel("\u015Eifre *");
		lbl_hPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lbl_hPass.setBounds(33, 144, 72, 21);
		contentPane.add(lbl_hPass);

		JButton btn_register = new JButton(new ImageIcon(getClass().getResource("/register.png")));
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (fld_hTC.getText().length() ==0 || fld_hPass.getText().length() ==0
						|| fld_hName.getText().length() == 0) {
					Helper.showMsg("complete");
				}
				else if(fld_hTC.getText().length() !=11 ){
					Helper.showMsg("TC kimlik numarasý 11 haneli olmalýdýr!");
				}
				else if(fld_hPass.getText().length() !=6) {
					Helper.showMsg("Þifre 6 haneli olmalýdýr!");
				}else {
					try {
						boolean control =user.addRegister(fld_hTC.getText(), fld_hPass.getText(),
								fld_hName.getText(),fld_telephone.getText(),fld_address.getText(),type);
						if (control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_register.setBackground(Color.LIGHT_GRAY);
		btn_register.setBounds(128, 308, 80, 41);
		contentPane.add(btn_register);

		JButton btn_back = new JButton(new ImageIcon(getClass().getResource("/back.png")));
		btn_back.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {
		LoginGUI login = new LoginGUI();
		login.setVisible(true);
		dispose();
	}});btn_back.setFont(new Font("Yu Gothic UI Semibold",Font.PLAIN,15));btn_back.setBackground(Color.LIGHT_GRAY);btn_back.setBounds(33,308,80,41);contentPane.add(btn_back);

	JLabel lbl_hName_1 = new JLabel(
			"Kay\u0131t");
	lbl_hName_1.setHorizontalAlignment(SwingConstants.CENTER);lbl_hName_1.setFont(new Font("Yu Gothic UI Semibold",Font.BOLD,16));lbl_hName_1.setBounds(33,11,175,21);contentPane.add(lbl_hName_1);

	fld_hPass=new JPasswordField();fld_hPass.setBounds(33,163,175,30);contentPane.add(fld_hPass);
JLabel lbl_hTC_1 = new JLabel("Telefon Numaras\u0131");
lbl_hTC_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
lbl_hTC_1.setBounds(33, 193, 115, 21);
contentPane.add(lbl_hTC_1);
fld_telephone = new JTextField();
fld_telephone.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
fld_telephone.setColumns(10);
fld_telephone.setBounds(33, 212, 175, 30);
contentPane.add(fld_telephone);
JButton btnNewButton = new JButton(new ImageIcon(getClass().getResource("/help.png")));
btnNewButton.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		Helper.helpMsg("register");
	}
});
btnNewButton.setForeground(Color.BLACK);
btnNewButton.setBackground(Color.WHITE);
btnNewButton.setBounds(208, 11, 38, 35);
contentPane.add(btnNewButton);
JLabel lbl_hTC_1_1 = new JLabel("Adres");
lbl_hTC_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
lbl_hTC_1_1.setBounds(33, 241, 115, 21);
contentPane.add(lbl_hTC_1_1);
fld_address = new JTextField();
fld_address.setFont(new Font("Yu Gothic UI", Font.PLAIN, 18));
fld_address.setColumns(10);
fld_address.setBounds(33, 260, 175, 41);
contentPane.add(fld_address);
}	
}
