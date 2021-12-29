/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbhelper;

import Model.ModelFilm;
import Model.ModelStaff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class DAOStaff {
    private List<ModelStaff> listStaff;
    
    public List<ModelStaff> getAllStaff() {
        listStaff = new ArrayList<>();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM staff");
                while (result.next()) {
                    ModelStaff staff  = new ModelStaff();
                    staff.setId_staff(result.getInt(1));
                    staff.setName(result.getString(2));
                    staff.setUsername(result.getString(3));
                    staff.setPassword(result.getString(4));
                    staff.setPhone(result.getString(5));
                    staff.setRole(result.getString(6));
                    
                    listStaff.add(staff);
                }
            }
            result.close();
            return listStaff;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }
    }
    public ModelStaff getStaff(String username, String password) {
        listStaff = new ArrayList<>();
        ModelStaff staff  = new ModelStaff();
        
        System.out.println(username + password);
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                System.out.println("ajndjansd");
                result = statement.executeQuery("SELECT * FROM staff where username = '"+username+"' and password = '"+ password + "'");
                while (result.next()) {                    
                    staff.setId_staff(result.getInt(1));
                    staff.setName(result.getString(2));
                    staff.setUsername(result.getString(3));
                    staff.setPassword(result.getString(4));
                    staff.setPhone(result.getString(5));
                    staff.setRole(result.getString(6));
                    
                    listStaff.add(staff);
                }
            }
            result.close();
            return staff;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }
    }
    
    public void insertStaff(ModelStaff Staff) {
        try {
            System.out.println(Staff.getPhone());
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO staff (id_staff, name, username, password, phone_number, role) VALUES (?,?,?,?,?,?)";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, null);
                statement.setString(2, Staff.getName());
                statement.setString(3, Staff.getUsername());
                statement.setString(4, Staff.getPassword());
                statement.setString(5, Staff.getPhone());
                statement.setString(6, Staff.getRole());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    public void updateStaff(ModelStaff Staff) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE staff SET name=?, username=?, password=?, phone_number=?, role=? WHERE id_staff=?";
            try(PreparedStatement statement = conn.prepareStatement(sql)){                
                statement.setString(1, Staff.getName());
                statement.setString(2, Staff.getUsername());
                statement.setString(3, Staff.getPassword());
                statement.setString(4, Staff.getPhone());
                statement.setString(5, Staff.getRole());
                statement.setInt(6, Staff.getId_staff());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    public void deleteStaff(int id_staff) {
        try {
            Connection connection = DBConnect.getConnection();
            String sql = "DELETE FROM staff WHERE id_staff=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id_staff);
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
}
