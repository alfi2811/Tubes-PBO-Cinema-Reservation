/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbhelper;

import Model.ModelStaff;
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
}
