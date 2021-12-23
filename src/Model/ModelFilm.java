package Model;

import java.sql.Date;

public class ModelFilm {
    private int id_film;
    private String title;
    private String genre;
    private Date date_start;
    private Date date_end;
    
    public ModelFilm() {}

    public ModelFilm(int id_film, String title, String genre, Date date_start, Date date_end) {
        this.id_film = id_film;
        this.title = title;
        this.genre = genre;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }
    
    
    
}
