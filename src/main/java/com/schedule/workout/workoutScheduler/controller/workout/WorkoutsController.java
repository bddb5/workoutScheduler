package com.schedule.workout.workoutScheduler.controller.workout;

import com.schedule.workout.workoutScheduler.controller.user.UpdateUserModel;
import com.schedule.workout.workoutScheduler.controller.user.UserModel;
import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.exceptions.InvalidUserBodyException;
import com.schedule.workout.workoutScheduler.exceptions.InvalidWorkoutBodyException;
import com.schedule.workout.workoutScheduler.exceptions.UserNotFoundException;
import com.schedule.workout.workoutScheduler.exceptions.WorkoutNotFoundException;
import com.schedule.workout.workoutScheduler.services.WorkoutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class WorkoutsController {
    @Autowired
    private WorkoutsService workoutsService;

    @RequestMapping(method = RequestMethod.POST, value = "/workouts")
    public ResponseEntity<CreateWorkoutModel> createWorkout(@Valid @RequestBody CreateWorkoutModel createWorkoutModel){
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(workoutsService.createWorkout(createWorkoutModel));
        } catch (InvalidWorkoutBodyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/workouts")
    public ResponseEntity<List<WorkoutModel>> getAllWorkouts(@RequestParam(name = "name",required = false)String name,
                                                             @RequestParam(name = "description",required = false)String description,
                                                             @RequestParam(name = "duration",required = false) Integer duration,
                                                             @RequestParam(name = "difficulty",required = false)Integer difficulty
                                                            ){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workoutsService.getAllWorkouts(name, description, duration, difficulty));
        }catch (WorkoutNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
            }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/workouts/{id}")
    public ResponseEntity<WorkoutModel> getWorkoutById(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workoutsService.getWorkoutById(id));
        }catch (WorkoutNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/workouts/{id}")
    public ResponseEntity<UpdateWorkoutModel> updateWorkout(@PathVariable String id, @Valid @RequestBody UpdateWorkoutModel updateWorkoutModel) {
        try {
            workoutsService.updateWorkout(id,updateWorkoutModel);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        } catch (WorkoutNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (InvalidWorkoutBodyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/workouts/{id}")
    public ResponseEntity<WorkoutModel> deleteWorkout(@PathVariable String id) {
        try {
            workoutsService.deleteWorkout(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (WorkoutNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

}
