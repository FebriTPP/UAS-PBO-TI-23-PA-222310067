package com.ibik.pbo.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private String queryInsert = "INSERT INTO admins (fullName, email, password, gender) VALUES (?, ?, ?, ?)";
    private String querySelectAll = "SELECT * FROM admins";
    private String queryUpdateById = "UPDATE admins SET fullName=?, email=?, password=? WHERE id_admin=?";
    private String queryDeleteById = "DELETE FROM admins WHERE id_admin=?";
    private String queryFindByEmail = "SELECT * FROM admins WHERE email=?";
    private String queryFindById = "SELECT * FROM admins WHERE id_admin=?";

   
    public void insert(Admin admin) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psInsert = c.prepareStatement(queryInsert);
        psInsert.setString(1, admin.getFullName());
        psInsert.setString(2, admin.getEmail());
        psInsert.setString(3, admin.getPassword());
        psInsert.setString(4, admin.getGender());


        psInsert.executeUpdate();
        c.close();
    }

   
    public List<Admin> getAllAdmins() throws Exception {
        List<Admin> adminList = new ArrayList<>();
        Connection c = new ConnectionDB().connect();

        PreparedStatement psSelectAll = c.prepareStatement(querySelectAll);
        ResultSet rs = psSelectAll.executeQuery();

        while (rs.next()) {
            Admin admin = convertResultSetToAdmin(rs);
            adminList.add(admin);
        }

        c.close();
        return adminList;
    }

   
    public void updateById(Admin admin) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psUpdate = c.prepareStatement(queryUpdateById);
        psUpdate.setString(1, admin.getFullName());
        psUpdate.setString(2, admin.getEmail());
        psUpdate.setString(3, admin.getPassword());
        psUpdate.setString(4, admin.getGender());

        psUpdate.setInt(4, admin.getIdAdmin());

        psUpdate.executeUpdate();
        c.close();
    }

    
    public void deleteById(int idAdmin) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psDelete = c.prepareStatement(queryDeleteById);
        psDelete.setInt(1, idAdmin);

        psDelete.executeUpdate();
        c.close();
    }


    public Admin findByEmail(String email) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psFindByEmail = c.prepareStatement(queryFindByEmail);
        psFindByEmail.setString(1, email);

        ResultSet rs = psFindByEmail.executeQuery();

        Admin admin = null;
        if (rs.next()) {
            admin = convertResultSetToAdmin(rs);
        }

        c.close();
        return admin;
    }


    public Admin getAdminById(int idAdmin) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psFindById = c.prepareStatement(queryFindById);
        psFindById.setInt(1, idAdmin);

        ResultSet rs = psFindById.executeQuery();

        Admin admin = null;
        if (rs.next()) {
            admin = convertResultSetToAdmin(rs);
        }

        c.close();
        return admin;
    }

  
    private Admin convertResultSetToAdmin(ResultSet rs) throws Exception {
        Admin admin = new Admin();
        admin.setIdAdmin(rs.getInt("id_admin"));
        admin.setFullName(rs.getString("fullName"));
        admin.setEmail(rs.getString("email"));
        admin.setPassword(rs.getString("password"));
        admin.setGender(rs.getString("gender"));

        return admin;
    }
}
