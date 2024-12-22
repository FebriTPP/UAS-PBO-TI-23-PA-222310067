package com.ibik.pbo.applications;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ibik.pbo.connections.ConnectionDB;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
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
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JButton TestDBbtn = new JButton("Test DB");
        TestDBbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Membuat koneksi ke database
                    Connection c = new ConnectionDB().connect();

                    // Mengecek apakah koneksi berhasil
                    if (c != null && !c.isClosed()) {
                        JOptionPane.showMessageDialog(null, "Koneksi Database Berhasil!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Koneksi Database Gagal!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e1) {
                    // Menangkap exception dan menampilkan pesan error
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace(); // Untuk debugging
                } catch (Exception ex) {
                    // Menangkap exception lain yang mungkin terjadi
                    JOptionPane.showMessageDialog(null, "Kesalahan Tidak Diketahui: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JButton loginAdmin = new JButton("Login sebagai admin");
        loginAdmin.setBackground(new Color(255, 255, 128));
        loginAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage(); 
                loginPage.setVisible(true);
                dispose(); 
            }
        });

        JButton loginSiswa = new JButton("Login sebagai siswa");
        loginSiswa.setBackground(new Color(128, 255, 128));
        loginSiswa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage(); 
                loginPage.setVisible(true);
                dispose(); 
            }
        });

        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.CENTER)
                .addComponent(TestDBbtn, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(loginAdmin, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(loginSiswa, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(TestDBbtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(loginAdmin, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(loginSiswa, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        );
    }
}
