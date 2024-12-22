package com.ibik.pbo.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TugasUsersDao {

    // Query SQL
    private final String queryInsert = "INSERT INTO tugas_siswa (nama_siswa, file_siswa) VALUES (?, ?)";
    private final String querySelectAll = "SELECT * FROM tugas_siswa";
    private final String queryFindById = "SELECT * FROM tugas_siswa WHERE id_tugas_siswa = ?";
    private final String queryUpdate = "UPDATE tugas_siswa SET nama_siswa = ?, file_siswa = ? WHERE id_tugas_siswa = ?";
    private final String queryDeleteById = "DELETE FROM tugas_siswa WHERE id_tugas_siswa = ?";


    public void save(TugasUsers tugasUser) throws Exception {
        Connection c = new ConnectionDB().connect();
        PreparedStatement ps = c.prepareStatement(queryInsert);
        ps.setString(1, tugasUser.getNama_siswa());
        ps.setBytes(2, tugasUser.getFile_siswa());

        ps.executeUpdate();
        c.close();
    }

    
    public List<TugasUsers> findAll() throws Exception {
        List<TugasUsers> tugasUsers = new ArrayList<>();
        Connection c = new ConnectionDB().connect();
        PreparedStatement ps = c.prepareStatement(querySelectAll);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            TugasUsers tugasUser = konversiResultSet(rs);
            tugasUsers.add(tugasUser);
        }

        c.close();
        return tugasUsers;
    }

  
    public TugasUsers findById(int idTugasSiswa) throws Exception {
        Connection c = new ConnectionDB().connect();
        PreparedStatement ps = c.prepareStatement(queryFindById);
        ps.setInt(1, idTugasSiswa);

        ResultSet rs = ps.executeQuery();
        TugasUsers tugasUser = null;
        if (rs.next()) {
            tugasUser = konversiResultSet(rs);
        }

        c.close();
        return tugasUser;
    }


    public void update(TugasUsers tugasUser) throws Exception {
        Connection c = new ConnectionDB().connect();
        PreparedStatement ps = c.prepareStatement(queryUpdate);
        ps.setString(1, tugasUser.getNama_siswa());
        ps.setBytes(2, tugasUser.getFile_siswa());
        ps.setInt(3, tugasUser.getId_tugas_siswa());

        ps.executeUpdate();
        c.close();
    }


    public void deleteById(int idTugasSiswa) throws Exception {
        Connection c = new ConnectionDB().connect();
        PreparedStatement ps = c.prepareStatement(queryDeleteById);
        ps.setInt(1, idTugasSiswa);

        ps.executeUpdate();
        c.close();
    }

    private TugasUsers konversiResultSet(ResultSet rs) throws Exception {
        TugasUsers tugasUser = new TugasUsers();
        tugasUser.setId_tugas_siswa(rs.getInt("id_tugas_siswa"));
        tugasUser.setNama_siswa(rs.getString("nama_siswa"));
        tugasUser.setFile_siswa(rs.getBytes("file_siswa"));
        return tugasUser;
    }
}
