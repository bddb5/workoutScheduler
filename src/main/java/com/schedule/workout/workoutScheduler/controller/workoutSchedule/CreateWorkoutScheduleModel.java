package com.schedule.workout.workoutScheduler.controller.workoutSchedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.schedule.workout.workoutScheduler.database.model.WorkoutDB;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class CreateWorkoutScheduleModel {


    @NotNull
    @Min(value = 1)
    @Max(value = 7)
    private Integer day;
    @NotNull
    private Time startWorkout;
    @NotEmpty
    private String workoutID;

    public CreateWorkoutScheduleModel() {

    }

    public CreateWorkoutScheduleModel(Integer day, Time startWorkout, String workoutID) {
        this.day = day;
        this.startWorkout = startWorkout;
        this.workoutID = workoutID;
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
