package com.schedule.workout.workoutScheduler.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="workouts")
public class WorkoutDB {

    @Id
    @Column(name = "workout_id")
    private String id;

    @NotEmpty
    @Size(min=2, max=20)
    @Column(name = "name")
    private String name;
    @NotEmpty
    @Size(min=2, max=80)
    @Column(name = "description")
    private String description;
    @NotNull
    @Min(value = 20)
    @Max(value = 60)
    @Column(name = "duration")
    private Integer duration;
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "difficulty")
    private Integer difficulty;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private UserDB user;


    public WorkoutDB(){

    }
    public WorkoutDB(String id,String name,String description,Integer duration,Integer difficulty,UserDB user){
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.difficulty = difficulty;
        this.user = user;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getTrainerId() {
        return user.getId();
    }
    public String getUserFullName(){
        return user.getFirstName() + " " + user.getLastName();
    }
    @JsonIgnore
    public UserDB getUser() {
        return user;
    }
    @JsonIgnore
    public void setUser(UserDB user) {
        this.user = user;
    }
}
