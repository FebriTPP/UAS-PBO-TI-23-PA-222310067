package com.ibik.pbo.applications;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.ibik.pbo.connections.Admin;
import com.ibik.pbo.connections.AdminDao;
import com.ibik.pbo.connections.Users;
import com.ibik.pbo.connections.UsersDao;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailField;
	private JPasswordField passwordField;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public LoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel email = new JLabel("Email");
		email.setBounds(48, 36, 49, 14);
		contentPane.add(email);
		
		JLabel password = new JLabel("Password");
		password.setBounds(48, 81, 85, 14);
		contentPane.add(password);
		
	
		
		JButton loginButton = new JButton("Submit");
		loginButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String email = emailField.getText();
		            String password = new String(passwordField.getPassword());

		            if (email.isEmpty() || password.isEmpty()) {
		                JOptionPane.showMessageDialog(contentPane, "Email & Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
		                return;
		            }

		           
		            AdminDao adminDao = new AdminDao();
		            Admin admin = adminDao.findByEmail(email);

		            if (admin != null && admin.getPassword().equals(password)) {
		                JOptionPane.showMessageDialog(contentPane, "Login berhasil sebagai Admin!", "Success", JOptionPane.INFORMATION_MESSAGE);

		           
		                AdminPage adminPage = new AdminPage();
		                adminPage.setVisible(true);
		                dispose();
		                return;
		            }

		            // Periksa di tabel Users
		            UsersDao usersDao = new UsersDao();
		            Users user = usersDao.findByEmail(email);

		            if (user != null && user.getPassword().equals(password)) {
		                JOptionPane.showMessageDialog(contentPane, "Login berhasil sebagai User!", "Success", JOptionPane.INFORMATION_MESSAGE);

		                // Redirect ke UserPage
		                UserPage userPage = new UserPage();
		                userPage.setVisible(true);
		                dispose();
		                return;
		            }

		            // Jika tidak ditemukan di kedua tabel
		            JOptionPane.showMessageDialog(contentPane, "Email atau password salah!", "Warning", JOptionPane.WARNING_MESSAGE);

		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(contentPane, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		loginButton.setBounds(44, 138, 89, 23);
		contentPane.add(loginButton);

		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPage registerPage = new RegisterPage(); 
		        registerPage.setVisible(true);
		        dispose(); 
			}
		});
		registerButton.setBounds(143, 138, 89, 23);
		contentPane.add(registerButton);
		
		emailField = new JTextField();
		emailField.setBounds(48, 50, 138, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(48, 93, 138, 20);
		contentPane.add(passwordField);
	}
}
