package com.ibik.pbo.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TugasDao {

    private String queryInsert = "INSERT INTO tugas_guru (judul, deskripsi, file_tugas) VALUES (?, ?, ?)";
    private String querySelectAll = "SELECT * FROM tugas_guru";
    private String queryUpdate = "UPDATE tugas_guru SET judul=?, deskripsi=?, file=? WHERE id_tugas = ?";
    private String queryRemoveById = "DELETE FROM tugas_guru WHERE id_tugas = ?";
    private String queryFindById = "SELECT * FROM tugas_guru WHERE id_tugas = ?";
    private String queryFindByJudul = "SELECT * FROM tugas_guru WHERE judul LIKE ?";
    private String queryRemoveByJudul = "DELETE FROM tugas_guru WHERE judul = ?";



    public void save(Tugas tugas) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psInsert = c.prepareStatement(queryInsert);
        psInsert.setString(1, tugas.getJudul());
        psInsert.setString(2, tugas.getDeskripsi());
        psInsert.setBytes(3, tugas.getFile_tugas());
   ;

        psInsert.executeUpdate();
        c.close();
    }

    public List<Tugas> findAll() throws Exception {
        List<Tugas> tugasList = new ArrayList<>();
        Connection c = new ConnectionDB().connect();

        PreparedStatement psSelectAll = c.prepareStatement(querySelectAll);
        ResultSet rs = psSelectAll.executeQuery();

        while (rs.next()) {
            Tugas tugas = konversiResultSet(rs);
            tugasList.add(tugas);
        }

        c.close();
        return tugasList;
    }

    public void update(Tugas tugas) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psUpdate = c.prepareStatement(queryUpdate);
        psUpdate.setString(1, tugas.getJudul());
        psUpdate.setString(2, tugas.getDeskripsi());
        psUpdate.setBytes(3, tugas.getFile_tugas());
        psUpdate.setInt(4, tugas.getId_tugas());

        psUpdate.executeUpdate();
        c.close();
    }

    public void removeById(Integer idTugas) throws Exception {
        if (idTugas == null) {
            return;
        }

        Connection c = new ConnectionDB().connect();

        PreparedStatement psRemove = c.prepareStatement(queryRemoveById);
        psRemove.setInt(1, idTugas);

        psRemove.executeUpdate();
        c.close();
    }

    public Tugas findById(int idTugas) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psFindById = c.prepareStatement(queryFindById);
        psFindById.setInt(1, idTugas);

        ResultSet rs = psFindById.executeQuery();

        Tugas tugas = null;
        if (rs.next()) {
            tugas = konversiResultSet(rs);
        }

        c.close();
        return tugas;
    }
    
    public Tugas findByJudul(String judul) throws Exception {
        if (judul == null || judul.isEmpty()) {
            throw new IllegalArgumentException("Judul tidak boleh kosong.");
        }

        Connection c = new ConnectionDB().connect();

        PreparedStatement psFindByJudul = c.prepareStatement(queryFindByJudul);
        psFindByJudul.setString(1, "%" + judul + "%"); 
        ResultSet rs = psFindByJudul.executeQuery();

        Tugas tugas = null;
        if (rs.next()) { 
            tugas = konversiResultSet(rs);
        }

        c.close();
        return tugas;
    }

    
    
    public void deleteByJudul(String judul) throws Exception {
        if (judul == null || judul.isEmpty()) {
            throw new IllegalArgumentException("Judul tidak boleh kosong.");
        }

        Connection c = new ConnectionDB().connect();

        PreparedStatement psRemoveByJudul = c.prepareStatement(queryRemoveByJudul);
        psRemoveByJudul.setString(1, judul);

        int rowsAffected = psRemoveByJudul.executeUpdate();

        if (rowsAffected == 0) {
            System.out.println("Tidak ada data yang dihapus. Judul tidak ditemukan.");
        } else {
            System.out.println(rowsAffected + " baris berhasil dihapus.");
        }

        c.close();
    }


    private Tugas konversiResultSet(ResultSet rs) throws Exception {
        Tugas tugas = new Tugas();
        tugas.setId_tugas(rs.getInt("id_tugas"));
        tugas.setJudul(rs.getString("judul"));
        tugas.setDeskripsi(rs.getString("deskripsi"));
        tugas.setFile_tugas(rs.getBytes("file_tugas"));
        return tugas;
    }
    
    
}
