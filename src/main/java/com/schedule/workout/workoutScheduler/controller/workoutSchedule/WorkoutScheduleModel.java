package com.schedule.workout.workoutScheduler.controller.workoutSchedule;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Time;

public class WorkoutScheduleModel {

    @NotNull
    private String id;
    @NotNull
    @Min(value = 1)
    @Max(value = 7)
    private Integer day;
    @NotNull
    private Time startWorkout;
    @NotEmpty
    private String workoutID;

    public WorkoutScheduleModel() {

    }

    public WorkoutScheduleModel(String id, Integer day, Time startWorkout, String workoutID) {
        this.id = id;
        this.day = day;
        this.startWorkout = startWorkout;
        this.workoutID = workoutID;

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

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

}
