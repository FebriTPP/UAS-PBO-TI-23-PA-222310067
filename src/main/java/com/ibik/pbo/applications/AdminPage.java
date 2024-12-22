package com.ibik.pbo.applications;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ibik.pbo.connections.Tugas;
import com.ibik.pbo.connections.TugasDao;

import java.io.File;
import java.nio.file.Files;
public class AdminPage extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField judulField;
    private JTextField deskripsiField;
    private JTable adminTable;
    private DefaultTableModel tableModel;
    private byte[] uploadedFile;
    private boolean modeEdit = false;
    private int indeksBaris = -1;

    public AdminPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 720, 406);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

     
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(128, 255, 0));
        addButton.setBounds(27, 281, 111, 39);
        contentPane.add(addButton);

     
        JButton deleteButton = new JButton("Delete");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.RED);
        deleteButton.setBounds(153, 281, 111, 39);
        contentPane.add(deleteButton);

       
        JButton editButton = new JButton("Edit");
        editButton.setForeground(new Color(0, 0, 128));
        editButton.setBackground(new Color(128, 255, 255));
        editButton.setBounds(287, 281, 111, 39);
        contentPane.add(editButton);

       
        String[] columnNames = {"Judul", "Deskripsi", "File"};
        tableModel = new DefaultTableModel(columnNames, 0); 
        adminTable = new JTable(tableModel);

        
        JScrollPane scrollPane = new JScrollPane(adminTable);
        scrollPane.setBounds(346, 49, 360, 221);
        contentPane.add(scrollPane);

       
        JLabel judulLabel = new JLabel("Judul Tugas");
        judulLabel.setBounds(10, 83, 111, 28);
        contentPane.add(judulLabel);

        judulField = new JTextField();
        judulField.setBounds(131, 85, 173, 24);
        contentPane.add(judulField);

        JLabel deskripsiLabel = new JLabel("Deskripsi Tugas");
        deskripsiLabel.setBounds(10, 120, 111, 28);
        contentPane.add(deskripsiLabel);

        deskripsiField = new JTextField();
        deskripsiField.setBounds(132, 122, 173, 24);
        contentPane.add(deskripsiField);

     
        JButton uploadButton = new JButton("Upload File");
        uploadButton.setBounds(73, 176, 173, 28);
        contentPane.add(uploadButton);

        
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();
                        uploadedFile = Files.readAllBytes(file.toPath());
                        JOptionPane.showMessageDialog(null, "File berhasil ter upload");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error uploading file: " + ex.getMessage());
                    }
                }
            }
        });

      
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String judul = judulField.getText();
                String deskripsi = deskripsiField.getText();

                if (judul.isEmpty() || deskripsi.isEmpty() || uploadedFile == null) {
                    JOptionPane.showMessageDialog(null, "Semua field harus di isi!");
                    return;
                }

                try {
                    TugasDao tugasDao = new TugasDao();

                    if (modeEdit) {
                      
                        Tugas tugas = tugasDao.findByJudul((String) tableModel.getValueAt(indeksBaris, 0));
                        tugas.setJudul(judul);
                        tugas.setDeskripsi(deskripsi);
                        tugas.setFile_tugas(uploadedFile);
                        tugasDao.update(tugas);

                    
                        tableModel.setValueAt(judul, indeksBaris, 0);
                        tableModel.setValueAt(deskripsi, indeksBaris, 1);
                        tableModel.setValueAt("File uploaded", indeksBaris, 2);

                        modeEdit = false;
                        addButton.setText("Add");
                    } else {
                        
                        Tugas tugas = new Tugas();
                        tugas.setJudul(judul);
                        tugas.setDeskripsi(deskripsi);
                        tugas.setFile_tugas(uploadedFile);
                        tugasDao.save(tugas);

                       
                        tableModel.addRow(new Object[]{judul, deskripsi, "File uploaded"});
                    }

                    clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = adminTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete!");
                    return;
                }

                try {
                    String judul = (String) tableModel.getValueAt(selectedRow, 0);
                    TugasDao tugasDao = new TugasDao();
                    tugasDao.deleteByJudul(judul);
                    tableModel.removeRow(selectedRow);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

      
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = adminTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Pilih baris yang akan diedit!");
                    return;
                }

                modeEdit = true;
                indeksBaris = selectedRow;

                judulField.setText((String) tableModel.getValueAt(selectedRow, 0));
                deskripsiField.setText((String) tableModel.getValueAt(selectedRow, 1));
                addButton.setText("Save");
            }
        });

        loadData(); 
    }

    private void clearFields() {
        judulField.setText("");
        deskripsiField.setText("");
        uploadedFile = null;
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            TugasDao tugasDao = new TugasDao();
            for (Tugas tugas : tugasDao.findAll()) {
                tableModel.addRow(new Object[]{
                        tugas.getJudul(),
                        tugas.getDeskripsi(),
                        tugas.getFile_tugas() != null ? "File terupload" : "No file"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + ex.getMessage());
        }
    }
}
