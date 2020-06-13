package com.schedule.workout.workoutScheduler.controller.workoutSchedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.schedule.workout.workoutScheduler.controller.workout.UpdateWorkoutModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

public class UpdateWorkoutScheduleModel {
    @NotNull
    String day;
    @Temporal(TemporalType.TIME)
    //@DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
    @NotNull
    private Date startWorkout;
    @Temporal(TemporalType.TIME)
    //@DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "HH:mm")
    @NotNull
    private Date endWorkout;
    @NotEmpty
    private String workoutID;

    public UpdateWorkoutScheduleModel(){

    }
    public UpdateWorkoutScheduleModel(String day,Date startWorkout,Date endWorkout,String workoutID){
        this.day = day;
        this.startWorkout = startWorkout;
        this.endWorkout = endWorkout;
        this.workoutID = workoutID;

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

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }
}
