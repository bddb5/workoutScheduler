package com.schedule.workout.workoutScheduler.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user_role")
public class UserRoleDB {

    @Id
    @Column(name = "user_role_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserDB userDB;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleDB roleDB;

    @Column(name = "created_on")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date createdOn = new Date();

    public UserRoleDB() {

    }

    public UserRoleDB(String id, UserDB userDB, RoleDB roleDB, Date createdOn) {
        this.id = id;
        this.userDB = userDB;
        this.roleDB = roleDB;
        this.createdOn = createdOn;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public UserDB getUserDB() {
        return userDB;
    }

    @JsonIgnore
    public void setUserDB(UserDB userDB) {
        this.userDB = userDB;
    }

    @JsonIgnore
    public RoleDB getRoleDB() {
        return roleDB;
    }

    @JsonIgnore
    public void setRoleDB(RoleDB roleDB) {
        this.roleDB = roleDB;
    }

    public String getUserID() {
        return userDB.getId();
    }

    public String getRoleID() {
        return roleDB.getId();
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

}
