package models.user;

import com.google.gson.annotations.SerializedName;
import models.Role;

public class User {
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("phoneNumber")
    String phoneNumber;
    Role role;
    int Id;

    public User(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public User(int id) {
        Id = id;
    }

    public User(String firstName, String lastName, String phoneNumber, String userName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        Id = id;
    }

    public User(int id, String phoneNumber, Role role) {
        Id = id;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName=' " + firstName + '\'' +
                "lastName=' " + lastName + '\'' +
                "phoneNumber=' " + phoneNumber + '\'' +
                "role= " + role +
                "Id= " + Id +
                '}';
    }
}
