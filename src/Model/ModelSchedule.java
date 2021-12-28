package Model;

import java.sql.Date;

public class ModelSchedule {
    private int id_schedule;
    private int film_id;
    private int theater;
    private String time;
    private int price;

    public ModelSchedule() {}

    public ModelSchedule(int id_schedule, int film_id, int theater, String time, int price) {
        this.id_schedule = id_schedule;
        this.film_id = film_id;
        this.theater = theater;
        this.time = time;
        this.price = price;
    }

    public int getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(int id_schedule) {
        this.id_schedule = id_schedule;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getTheater() {
        return theater;
    }

    public void setTheater(int theater) {
        this.theater = theater;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
