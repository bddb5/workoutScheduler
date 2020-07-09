package com.schedule.workout.workoutScheduler.controller.userRole;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.schedule.workout.workoutScheduler.database.model.RoleDB;
import com.schedule.workout.workoutScheduler.database.model.UserDB;
import org.springframework.format.annotation.DateTimeFormat;

import javax.management.relation.Role;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserRoleModel {

    private String id;
    @NotEmpty
    private String userID;
    @NotEmpty
    private String roleID;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date createdOn;

    private UserDB user;
    private String userFullName;
    private RoleDB role;
    private String name;

    public UserRoleModel() {

    }

    public UserRoleModel(String id, String userID, String roleID, UserDB user, RoleDB role, Date createdOn) {
        this.id = id;
        this.userID = userID;
        this.roleID = roleID;
        this.user = user;
        this.role = role;
        this.createdOn = createdOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getRoleName() {
        return role.getName();
    }

    public void setRoleName(String name) {
        this.name = name;
    }
}
