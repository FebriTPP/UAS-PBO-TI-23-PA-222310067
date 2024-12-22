package com.ibik.pbo.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {

    private String queryInsert = "INSERT INTO users (fullname, email, password, gender) VALUES (?, ?, ?, ?)";
    private String querySelectAll = "SELECT * FROM users";
    private String queryUpdate = "UPDATE users SET fullname=?, email=?, password=?, gender=? WHERE id_user = ?";
    private String queryRemoveById = "DELETE FROM users WHERE id_user = ?";
    private String queryFindById = "SELECT * FROM users WHERE id_user = ?";

    
    private String queryFindByEmail = "SELECT * FROM users WHERE email = ?";

    public Users findByEmail(String email) throws Exception {
        Connection c = new ConnectionDB().connect();
        PreparedStatement psFindByEmail = c.prepareStatement(queryFindByEmail);
        psFindByEmail.setString(1, email);

        ResultSet rs = psFindByEmail.executeQuery();
        Users user = null;

        if (rs.next()) {
            user = konversiResultSet(rs);
        }

        c.close();
        return user;
    }

    
    public void save(Users user) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psInsert = c.prepareStatement(queryInsert);
        psInsert.setString(1, user.getFullname());
        psInsert.setString(2, user.getEmail());
        psInsert.setString(3, user.getPassword()); 
        psInsert.setString(4, user.getGender());

        psInsert.executeUpdate();
        c.close();
    }


    public List<Users> findAll() throws Exception {
        List<Users> usersList = new ArrayList<>();
        Connection c = new ConnectionDB().connect();

        PreparedStatement psSelectAll = c.prepareStatement(querySelectAll);
        ResultSet rs = psSelectAll.executeQuery();

        while (rs.next()) {
            Users user = konversiResultSet(rs);
            usersList.add(user);
        }

        c.close();
        return usersList;
    }

    public void update(Users user) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psUpdate = c.prepareStatement(queryUpdate);
        psUpdate.setString(1, user.getFullname());
        psUpdate.setString(2, user.getEmail());
        psUpdate.setString(3, user.getPassword());
        psUpdate.setString(4, user.getGender());
        psUpdate.setInt(5, user.getId());

        psUpdate.executeUpdate();
        c.close();
    }

  
    public void removeById(Integer id) throws Exception {
        if (id == null) {
            return;
        }

        Connection c = new ConnectionDB().connect();

        PreparedStatement psRemove = c.prepareStatement(queryRemoveById);
        psRemove.setInt(1, id);

        psRemove.executeUpdate();
        c.close();
    }

    public Users findById(int id) throws Exception {
        Connection c = new ConnectionDB().connect();

        PreparedStatement psFindById = c.prepareStatement(queryFindById);
        psFindById.setInt(1, id);

        ResultSet rs = psFindById.executeQuery();

        Users user = null;
        if (rs.next()) {
            user = konversiResultSet(rs);
        }

        c.close();
        return user;
    }


    private Users konversiResultSet(ResultSet rs) throws SQLException {
        Users user = new Users();
        user.setId(rs.getInt("id_user"));
        user.setFullname(rs.getString("fullname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setGender(rs.getString("gender"));
        return user;
    }
}
