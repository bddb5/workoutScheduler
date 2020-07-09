package com.schedule.workout.workoutScheduler.controller.role;

import com.schedule.workout.workoutScheduler.database.model.UserRoleDB;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateRoleModel {

    @NotEmpty
    @Size(max = 20)
    private String name;
    @NotEmpty
    @Size(max = 80)
    private String description;

    public CreateRoleModel() {

    }

    public CreateRoleModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
