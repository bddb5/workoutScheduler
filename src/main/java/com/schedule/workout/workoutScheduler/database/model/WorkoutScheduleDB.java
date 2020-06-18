package com.schedule.workout.workoutScheduler.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workoutSchedule")
public class WorkoutScheduleDB {

    @Id
    @Column(name = "schedule_id")
    private String id;
    @Column(name = "day")
    @NotNull
    @Min(value = 1)
    @Max(value = 7)
    private Integer day;
    @Column(name = "start_workout")
    @NotNull
    private Time startWorkout;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "workout_id")
    private WorkoutDB workoutDB;

    public WorkoutScheduleDB (){

    }
    public WorkoutScheduleDB(String id,Integer day,Time startWorkout,WorkoutDB workoutDB){
        this.id = id;
        this.day = day;
        this.startWorkout = startWorkout;
        this.workoutDB = workoutDB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Time getStartWorkout() {
        return startWorkout;
    }

    public void setStartWorkout(Time startWorkout) {
        this.startWorkout = startWorkout;
    }

    public String getWorkoutId() {
        return workoutDB.getId();
    }

    @JsonIgnore
    public WorkoutDB getWorkoutDB() {
        return workoutDB;
    }
    @JsonIgnore
    public void setWorkoutDB(WorkoutDB workoutDB) {
        this.workoutDB = workoutDB;
    }
}
