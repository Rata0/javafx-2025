package demo.demogia.model;

public class User {
    private int userID;
    private String fio;
    private String phone;
    private String login;
    private String password;
    private String type;

    public User(String fio, String phone, String login, String password, String type) {
        this.fio = fio;
        this.phone = phone;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", fio='" + fio + '\'' +
                ", phone='" + phone + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
