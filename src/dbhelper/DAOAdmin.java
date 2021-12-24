package dbhelper;

import Model.ModelFilm;
import Model.ModelSchedule;
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
    
    
    public void insertFilm(ModelFilm Film) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO film (id_film, title, genre, date_start, date_end) VALUES (?,?,?,?,?)";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, String.valueOf(Film.getId_film()));
                statement.setString(2, Film.getTitle());
                statement.setString(3, Film.getGenre());
                statement.setString(4, Film.getDate_start().toString());
                statement.setString(5, Film.getDate_end().toString());
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public void updateFilm(ModelFilm Film) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE film SET id_film=?, title=?, genre=?, date_start=?, date_end=?";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, String.valueOf(Film.getId_film()));
                statement.setString(2, Film.getTitle());
                statement.setString(3, Film.getGenre());
                statement.setString(4, Film.getDate_start().toString());
                statement.setString(5, Film.getDate_end().toString());
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
    
    public List<ModelFilm> getFilm(String title) {
        listFilm = new ArrayList<>();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM film WHERE title LIKE '%" + title + "%'");
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
                statement.setString(1, String.valueOf(Schedule.getId_schedule()));
                statement.setString(2, String.valueOf(Schedule.getFilm_id()));
                statement.setString(3, String.valueOf(Schedule.getTheater()));
                statement.setString(4, Schedule.getTime().toString());
                statement.setString(5, String.valueOf(Schedule.getPrice()));
                statement.executeUpdate();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
    
    public void updateSchedule(ModelSchedule Schedule) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE schedule SET id_schedule=?, film_id=?, theater=?, time=?, price=?";
            try(PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, String.valueOf(Schedule.getId_schedule()));
                statement.setString(2, String.valueOf(Schedule.getFilm_id()));
                statement.setString(3, String.valueOf(Schedule.getTheater()));
                statement.setString(4, Schedule.getTime().toString());
                statement.setString(5, String.valueOf(Schedule.getPrice()));
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
    
    public List<ModelSchedule> getSchedule(int film_id) {
        listSchedule = new ArrayList<>();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM schedule WHERE film_Id=?");
                while (result.next()) {
                    ModelSchedule schedule  = new ModelSchedule();
                    schedule.setId_schedule(result.getInt(1));
                    schedule.setFilm_id(result.getInt(2));
                    schedule.setTheater(result.getInt(3));
                    schedule.setTime(result.getDate(4));
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

    public List<ModelSchedule> getAllSchedule(int film_id) {
        listSchedule = new ArrayList<>();
        try {
            ResultSet result;
            try (Statement statement = DBConnect.getConnection().createStatement()) {
                result = statement.executeQuery("SELECT * FROM film WHERE film_Id=?");
                while (result.next()) {
                    ModelSchedule schedule  = new ModelSchedule();
                    schedule.setId_schedule(result.getInt(1));
                    schedule.setFilm_id(result.getInt(2));
                    schedule.setTheater(result.getInt(3));
                    schedule.setTime(result.getDate(4));
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
    
}
