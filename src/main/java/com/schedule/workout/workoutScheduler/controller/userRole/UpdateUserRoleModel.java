package com.schedule.workout.workoutScheduler.controller.userRole;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UpdateUserRoleModel {

    @NotEmpty
    private String userID;
    @NotEmpty
    private String roleID;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date createdOn;

    public UpdateUserRoleModel() {

    }

    public UpdateUserRoleModel(String userID, String roleID, Date createdOn) {
        this.userID = userID;
        this.roleID = roleID;
        this.createdOn = createdOn;
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
}

