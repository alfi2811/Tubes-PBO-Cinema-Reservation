package dbhelper;

import Model.ModelTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOKasir {
    public void insertTransaction(ModelTransaction Transaction) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO transaction (id_transaction, schedule_id, seat, total_price) VALUES (?,?,?,?)";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, String.valueOf(Transaction.getId_transaction()));
                statement.setString(2, String.valueOf(Transaction.getSchedule_id()));
                statement.setString(3, Transaction.getSeat());
                statement.setString(4, String.valueOf(Transaction.getTotal_price()));
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }    
}
