package com.schedule.workout.workoutScheduler.controller.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class UpdateUserModel {

    private String id;
    @NotEmpty
    @Size(min = 2, max = 10)
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 10)
    private String lastName;
    @NotNull
    @Min(value = 20)
    @Max(value = 100)
    private int age;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Column(name = "password")
    private String password;
    @NotEmpty
    @Size(min = 6, max = 10)
    private String phoneNumber;

    public UpdateUserModel() {
    }

    public UpdateUserModel(String id, String firstName, String lastName, int age, String email,String phoneNumber) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
