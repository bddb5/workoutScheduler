package com.schedule.workout.workoutScheduler.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "roles")
public class RoleDB {

    @Id
    @Column(name = "role_id")
    private String id;
    @NotEmpty
    @Size(max = 20)
    @Column(name = "name")
    private String name;
    @NotEmpty
    @Size(max = 80)
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "roleDB",cascade = CascadeType.ALL)
    private List<UserRoleDB> users = new ArrayList<>();

    public RoleDB(){

    }
    public RoleDB(String id,String name,String description){
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

    public List<UserRoleDB> getUsers() {
        return users;
    }
    public void setUsers(List<UserRoleDB> users) {
        this.users = users;
    }
}
