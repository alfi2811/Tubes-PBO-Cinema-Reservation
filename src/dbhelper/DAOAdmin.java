package dbhelper;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAdmin {
    private List<ModelFilm> listFilm;
    private List<ModelSchedule> listSchedule;
    private List<ModelTransaction> listTransaction;    
    
    
    public void insertFilm(ModelFilm Film) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO film (id_film, title, genre, date_start, date_end) VALUES (?,?,?,?,?)";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, null);
                statement.setString(2, Film.getTitle());
                statement.setString(3, Film.getGenre());
                statement.setDate(4, Film.getDate_start());
                statement.setDate(5, Film.getDate_end());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public void updateFilm(ModelFilm Film) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE film SET title=?, genre=?, date_start=?, date_end=? WHERE id_film=?";
            try(PreparedStatement statement = conn.prepareStatement(sql)){                
                statement.setString(1, Film.getTitle());
                statement.setString(2, Film.getGenre());
                statement.setString(3, Film.getDate_start().toString());
                statement.setString(4, Film.getDate_end().toString());
                statement.setInt(5, Film.getId_film());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public void deleteFilm(int id_film) {
        try {
            Connection connection = DBConnect.getConnection();
            String sql = "DELETE FROM film WHERE id_film=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id_film);
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public ModelFilm getFilm(String title) {
        listFilm = new ArrayList<>();
        ModelFilm film = new ModelFilm();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM film WHERE title LIKE '%" + title + "%'");
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
            return film;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }
    }
    
    public List<ModelFilm> getAllFilm() {
        listFilm = new ArrayList<>();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM film");
                while (result.next()) {
                    ModelFilm film  = new ModelFilm();
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
    
    public void insertSchedule(ModelSchedule Schedule) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO schedule (id_schedule, film_id, theater, time, price) VALUES (?,?,?,?,?)";            
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, null);
                statement.setInt(2, Schedule.getFilm_id());
                statement.setInt(3, Schedule.getTheater());
                statement.setString(4, Schedule.getTime());
                statement.setInt(5, Schedule.getPrice());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public void updateSchedule(ModelSchedule Schedule) {
        try {
            Connection conn = DBConnect.getConnection();            
            String sql = "UPDATE schedule SET theater=?, time=?, price=? WHERE id_schedule=?";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setInt(1, Schedule.getTheater());
                statement.setString(2, Schedule.getTime());
                statement.setInt(3, Schedule.getPrice());
                statement.setInt(4, Schedule.getId_schedule());
                
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public void deleteSchedule(int id_schedule) {
        try {
            Connection connection = DBConnect.getConnection();
            String sql = "DELETE FROM schedule WHERE id_schedule=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id_schedule);
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public ModelSchedule getSchedule(int film_id) {
        listSchedule = new ArrayList<>();
        ModelSchedule schedule  = new ModelSchedule();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM schedule WHERE film_Id=?");
                while (result.next()) {
                    schedule.setId_schedule(result.getInt(1));
                    schedule.setFilm_id(result.getInt(2));
                    schedule.setTheater(result.getInt(3));
                    schedule.setTime(result.getString(4));
                    schedule.setPrice(result.getInt(5));
                    listSchedule.add(schedule);
                }
            }
            result.close();
            return schedule;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }
    }

    public List<ModelSchedule> getAllSchedule(int film_id) {
        listSchedule = new ArrayList<>();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                String sql = "SELECT * FROM schedule WHERE film_Id="+ film_id;                
                result = statement.executeQuery(sql);
                while (result.next()) {
                    ModelSchedule schedule  = new ModelSchedule();
                    schedule.setId_schedule(result.getInt(1));
                    schedule.setFilm_id(result.getInt(2));
                    schedule.setTheater(result.getInt(3));
                    schedule.setTime(result.getString(4));
                    schedule.setPrice(result.getInt(5));
                    listSchedule.add(schedule);
                }
            }            
            result.close();
            return listSchedule;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }                
    }
    
    public List<ModelTransaction> getAllTransaction() {
        listTransaction = new ArrayList<>();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM transaction");
                while (result.next()) {
                    ModelTransaction transac  = new ModelTransaction();
                    transac.setId_transaction(result.getInt(1));
                    transac.setSchedule_id(result.getInt(2));
                    transac.setSeat(result.getString(3));
                    transac.setTotal_price(result.getInt(4));
                    transac.setDate_buy(result.getDate(5));
                    listTransaction.add(transac);
                }
            }
            result.close();
            return listTransaction;
        } catch (SQLException sqle) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, sqle);
            return null;
        }
    }
   
}
