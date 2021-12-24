package Model;

public class ModelTransaction {
    private int id_transaction;
    private int schedule_id;
    private String seat;
    private int total_price;
    
    public ModelTransaction() {}

    public ModelTransaction(int id_transaction, int schedule_id, String seat, int total_price) {
        this.id_transaction = id_transaction;
        this.schedule_id = schedule_id;
        this.seat = seat;
        this.total_price = total_price;
    }

    public int getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(int id_transaction) {
        this.id_transaction = id_transaction;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
