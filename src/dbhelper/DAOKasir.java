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
    
    public void insertTransaction(ModelTransaction Transaction) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO transaction (id_transaction, schedule_id, seat, total_price) VALUES (?,?,?,?)";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, null);
                statement.setInt(2, Transaction.getSchedule_id());
                statement.setString(3, Transaction.getSeat());
                statement.setInt(4, Transaction.getTotal_price());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    //Untuk menampilkan list film yang hari ini sedang tayang
    public List<ModelFilm> getNowShowing(Date date_start, Date date_end) {
        listFilm = new ArrayList<>();
        ModelFilm film = new ModelFilm();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                
                //Date today = <tanggal hari ini>
                result = statement.executeQuery("SELECT * FROM film WHERE date_start <= today AND date_end >= today");
                
                while (result.next()) {
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
}
