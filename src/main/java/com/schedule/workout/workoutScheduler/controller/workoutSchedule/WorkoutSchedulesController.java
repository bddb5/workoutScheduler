package com.schedule.workout.workoutScheduler.controller.workoutSchedule;

import com.schedule.workout.workoutScheduler.exceptions.InvalidWorkoutScheduleBodyException;
import com.schedule.workout.workoutScheduler.exceptions.WorkoutNotFoundException;
import com.schedule.workout.workoutScheduler.exceptions.WorkoutScheduleNotFoundException;
import com.schedule.workout.workoutScheduler.services.WorkoutSchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class WorkoutSchedulesController {
    @Autowired
    WorkoutSchedulesService workoutSchedulesService;

    @RequestMapping(method = RequestMethod.POST, value = "/schedules")
    public ResponseEntity<CreateWorkoutScheduleModel> createSchedule(@Valid @RequestBody CreateWorkoutScheduleModel createWorkoutScheduleModel) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(workoutSchedulesService.createSchedule(createWorkoutScheduleModel));
        } catch (InvalidWorkoutScheduleBodyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (WorkoutNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/schedules/{id}")
    public ResponseEntity updateSchedule(@PathVariable String id, @Valid @RequestBody UpdateWorkoutScheduleModel updateWorkoutScheduleModel) {
        try {
            workoutSchedulesService.updateSchedule(id, updateWorkoutScheduleModel);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (InvalidWorkoutScheduleBodyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }catch (WorkoutScheduleNotFoundException  | WorkoutNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schedules/{id}")
    public ResponseEntity<WorkoutScheduleModel> getWorkoutById(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workoutSchedulesService.getScheduleById(id));
        } catch (WorkoutScheduleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schedules")
    public ResponseEntity<List<WorkoutScheduleModel>> getAllSchedules
            (@RequestParam(name = "day", required = false) Integer day,
             @RequestParam(name = "workoutId", required = false) String workoutId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workoutSchedulesService.getAllSchedules(day, workoutId));
        } catch (WorkoutScheduleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/schedules/{id}")
    public ResponseEntity deleteSchedule(@PathVariable String id) {
        try {
            workoutSchedulesService.deleteSchedule(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (WorkoutScheduleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}