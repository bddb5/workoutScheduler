package com.schedule.workout.workoutScheduler.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    private String day;
    @Column(name = "start_workout")
    @Temporal(TemporalType.TIME)
    //@DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
    @NotNull
    private Date startWorkout;
    @Column(name = "end_workout")
    @Temporal(TemporalType.TIME)
    //@DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
    @NotNull
    private Date endWorkout;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "workout_id")
    private WorkoutDB workoutDB;

    public WorkoutScheduleDB (){

    }
    public WorkoutScheduleDB(String id,String day,Date startWorkout,Date endWorkout,WorkoutDB workoutDB){
        this.id = id;
        this.day = day;
        this.startWorkout = startWorkout;
        this.endWorkout = endWorkout;
        this.workoutDB = workoutDB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getStartWorkout() {
        return startWorkout;
    }

    public void setStartWorkout(Date startWorkout) {
        this.startWorkout = startWorkout;
    }

    public Date getEndWorkout() {
        return endWorkout;
    }

    public void setEndWorkout(Date endWorkout) {
        this.endWorkout = endWorkout;
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
