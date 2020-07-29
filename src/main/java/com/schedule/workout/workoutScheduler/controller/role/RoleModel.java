package com.schedule.workout.workoutScheduler.controller.role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RoleModel {

    private String id;
    @NotEmpty
    @Size(max = 20)
    private String name;
    @NotEmpty
    @Size(max = 80)
    private String description;

    public RoleModel() {

    }

    public RoleModel(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
