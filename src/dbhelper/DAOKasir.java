package dbhelper;

import Model.ModelFilm;
import Model.ModelTransaction;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOKasir {
    private List<ModelFilm> listFilm;
    private List<String> listSeat;    
    
    public void insertTransaction(ModelTransaction Transaction) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO transaction (id_transaction, schedule_id, seat, total_price, date_buy) VALUES (?,?,?,?,?)";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, null);
                statement.setInt(2, Transaction.getSchedule_id());
                statement.setString(3, Transaction.getSeat());
                statement.setInt(4, Transaction.getTotal_price());
                statement.setDate(5, Transaction.getDate_buy());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    //Untuk menampilkan list film yang hari ini sedang tayang
    public List<ModelFilm> getNowShowing(Date date_now) {
        listFilm = new ArrayList<>();        
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {                
                //Date today = <tanggal hari ini>
                result = statement.executeQuery("SELECT * FROM `film` WHERE `date_start` <= '"+ date_now +"' AND `date_end` >= '"+ date_now +"'");
                while (result.next()) {
                    ModelFilm film = new ModelFilm();
                    film.setId_film(result.getInt(1));
                    film.setTitle(result.getString(2));
                    film.setGenre(result.getString(3));
                    film.setDate_start(result.getDate(4));
                    film.setDate_end(result.getDate(5));
                    listFilm.add(film);
                }
            }
            result.close();            
            return listFilm;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }
    }
    
    //Untuk menampilkan list seat yang tersedia
    public List<String> getSeatAvailable(int schedule_id, Date date_buy) {
        listSeat = new ArrayList<>();        
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {                
                //Date today = <tanggal hari ini>
                result = statement.executeQuery("SELECT `seat` FROM `transaction` WHERE `schedule_id` = "+ schedule_id +" AND `date_buy` = '"+ date_buy +"'");
                while (result.next()) {
                    String seat = result.getString(1);                    
                    listSeat.add(seat);
                }
            }
            result.close();            
            return listSeat;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }
    }
}
