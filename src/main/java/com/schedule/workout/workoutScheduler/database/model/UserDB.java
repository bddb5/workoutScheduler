package com.schedule.workout.workoutScheduler.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDB {

    @Id
    @Column(name = "user_id")
    private String id;
    @NotEmpty
    @Size(min = 2, max = 10)
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 10)
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @Column(name = "age")
    @Min(value = 20)
    @Max(value = 100)
    private int age;
    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;
    @NotEmpty
    @Column(name = "password")
    private String password;
    @NotEmpty
    @Column(name = "phone_number")
    @Size(min = 6, max = 10)
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "userDB",cascade = CascadeType.ALL)
    private List<UserRoleDB> roles = new ArrayList<>();

    public UserDB() {
    }

    public UserDB(String id, String firstName, String lastName, int age, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<UserRoleDB> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleDB> roles) {
        this.roles = roles;
    }
}

