package Model;

import java.sql.Date;

public class Volunteer {
    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    String status;
    int id;
    Date dateJoined;

    public Volunteer(String firstName, String lastName, String email, String password,
                     String phoneNumber, String status, int id, Date dateJoined) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.id = id;
        this.dateJoined = dateJoined;
    }

    public Volunteer() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = Date.valueOf(dateJoined);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
