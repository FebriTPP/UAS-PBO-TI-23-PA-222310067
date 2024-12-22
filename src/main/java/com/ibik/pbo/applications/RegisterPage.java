package com.ibik.pbo.applications;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ibik.pbo.connections.Admin;
import com.ibik.pbo.connections.AdminDao;
import com.ibik.pbo.connections.Users;
import com.ibik.pbo.connections.UsersDao;

public class RegisterPage extends JFrame {
    private JPanel contentPane;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton maleLabel;
    private JRadioButton femaleLabel;
    private JComboBox<String> typeCombo;

    public RegisterPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel fullName = new JLabel("Fullname");
        fullName.setBounds(23, 34, 59, 14);
        contentPane.add(fullName);

        fullNameField = new JTextField();
        fullNameField.setBounds(108, 31, 136, 20);
        contentPane.add(fullNameField);
        fullNameField.setColumns(10);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(23, 62, 59, 14);
        contentPane.add(emailLabel);

        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(108, 59, 136, 20);
        contentPane.add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(23, 94, 81, 14);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(108, 90, 136, 20);
        contentPane.add(passwordField);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setBounds(23, 119, 59, 14);
        contentPane.add(genderLabel);

        maleLabel = new JRadioButton("Male");
        maleLabel.setBounds(81, 115, 59, 23);
        contentPane.add(maleLabel);

        femaleLabel = new JRadioButton("Female");
        femaleLabel.setBounds(148, 117, 136, 23);
        contentPane.add(femaleLabel);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleLabel);
        genderGroup.add(femaleLabel);

        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(155, 230, 89, 23);
        contentPane.add(btnRegister);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerAccount();
            }
        });
        
        JLabel typeLabel = new JLabel("Account Type:");
        typeLabel.setBounds(50, 180, 100, 25);
        contentPane.add(typeLabel);

        String[] types = {"User", "Admin"};
        typeCombo = new JComboBox<>(types); 
        typeCombo.setBounds(150, 180, 150, 25);
        contentPane.add(typeCombo);

    }

    
    
    private void registerAccount() {
        try {
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String gender = maleLabel.isSelected() ? "Male" : femaleLabel.isSelected() ? "Female" : "";
            String accountType = (String) typeCombo.getSelectedItem();

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi data dengan benar", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accountType.equals("User")) {
                Users user = new Users();
                user.setFullname(fullName);
                user.setEmail(email);
                user.setPassword(password);
                user.setGender(gender);

                UsersDao usersDao = new UsersDao();
                usersDao.save(user);
            } else if (accountType.equals("Admin")) {
                Admin admin = new Admin();
                admin.setFullName(fullName);
                admin.setEmail(email);
                admin.setPassword(password);
                admin.setGender(gender);

                AdminDao adminDao = new AdminDao();
                adminDao.insert(admin);
            }

            JOptionPane.showMessageDialog(this, "Akun berhasil didaftarkan!", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            LoginPage loginPage = new LoginPage(); 
	        loginPage.setVisible(true);
	        dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    public static void main(String[] args) {
        new RegisterPage().setVisible(true);
    }

}
