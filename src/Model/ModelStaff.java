package Model;

public class ModelStaff {
    private int id_staff;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String role;

    public ModelStaff() {}

    public ModelStaff(int id_staff, String name, String username, String password, String phone, String role) {
        this.id_staff = id_staff;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }        

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
