package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.controller.user.UserModel;
import com.schedule.workout.workoutScheduler.controller.workout.CreateWorkoutModel;
import com.schedule.workout.workoutScheduler.controller.workout.UpdateWorkoutModel;
import com.schedule.workout.workoutScheduler.controller.workout.WorkoutModel;
import com.schedule.workout.workoutScheduler.database.IUsersRepository;
import com.schedule.workout.workoutScheduler.database.IWorkoutsRepository;
import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.database.model.WorkoutDB;
import com.schedule.workout.workoutScheduler.exceptions.InvalidUserBodyException;
import com.schedule.workout.workoutScheduler.exceptions.InvalidWorkoutBodyException;
import com.schedule.workout.workoutScheduler.exceptions.UserNotFoundException;
import com.schedule.workout.workoutScheduler.exceptions.WorkoutNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicListUI;
import java.util.*;

import static java.util.Arrays.asList;

@Service
public class WorkoutsService {
    @Autowired
    private IWorkoutsRepository workoutsRepository;
    @Autowired
    private IUsersRepository usersRepository;


    //duration
    List<Integer> durationOfWorkout = asList(20,30,45,60);


    //create  workout
    public CreateWorkoutModel createWorkout(CreateWorkoutModel createWorkoutModel) {
        WorkoutDB workoutDB = new WorkoutDB();
        if (usersRepository.findById(createWorkoutModel.getUser().getId()).isPresent()) {
            UserDB user = usersRepository.findById(createWorkoutModel.getUser().getId()).get();
            workoutDB.setId(UUID.randomUUID().toString());
            workoutDB.setName(createWorkoutModel.getName());
            workoutDB.setDescription(createWorkoutModel.getDescription());

            if (durationOfWorkout.contains(createWorkoutModel.getDuration())) {
                workoutDB.setDuration(createWorkoutModel.getDuration());
            } else {
                throw new InvalidWorkoutBodyException();
            }
            workoutDB.setDifficulty(createWorkoutModel.getDifficulty());
            workoutDB.setUser(user);
            workoutsRepository.save(workoutDB);
            return new CreateWorkoutModel(workoutDB.getId(), workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(), workoutDB.getDifficulty(), workoutDB.getUser());
        } else {
            throw new UserNotFoundException();
        }
    }
    //get all workouts/filters
    public List<WorkoutModel> getAllWorkouts(String name, String description, Integer duration, Integer difficulty) {
        List<WorkoutModel> workoutList = new ArrayList<>();

        if (name != null) {
            workoutsRepository.findWorkoutsByName(name).forEach(workoutDB -> workoutList.add(new WorkoutModel(workoutDB.getId(), workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(), workoutDB.getDifficulty(),workoutDB.getUser())));
        }else if (description != null) {
            workoutsRepository.findWorkoutsByDescription(description).forEach(workoutDB -> workoutList.add(new WorkoutModel(workoutDB.getId(), workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(), workoutDB.getDifficulty(), workoutDB.getUser())));
        }else if (duration != null) {
            workoutsRepository.findWorkoutsByDuration(duration).forEach(workoutDB -> workoutList.add(new WorkoutModel(workoutDB.getId(), workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(), workoutDB.getDifficulty(), workoutDB.getUser())));
        }else if(difficulty != null){
            workoutsRepository.findWorkoutsByDifficulty(difficulty).forEach(workoutDB -> workoutList.add(new WorkoutModel(workoutDB.getId(), workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(), workoutDB.getDifficulty(), workoutDB.getUser())));
        }else {
            workoutsRepository.findAll().forEach(workoutDB -> workoutList.add(new WorkoutModel(workoutDB.getId(), workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(), workoutDB.getDifficulty(), workoutDB.getUser())));
        }
        return workoutList;
    }
    //get workout by id
    public WorkoutModel getWorkoutById(String id) {
        if (workoutsRepository.findById(id).isPresent()) {
            WorkoutDB workoutById = workoutsRepository.findById(id).get();
            return new WorkoutModel(workoutById.getId(), workoutById.getName(), workoutById.getDescription(), workoutById.getDuration(), workoutById.getDifficulty(), workoutById.getUser());
        } else {
            throw new WorkoutNotFoundException();
        }
    }
    //update workout
    public UpdateWorkoutModel updateWorkout(String id, UpdateWorkoutModel updateWorkoutModel) {
        if (workoutsRepository.findById(id).isPresent()) {
            WorkoutDB workoutDBtoUpdate = workoutsRepository.findById(id).get();
            workoutDBtoUpdate.setName(updateWorkoutModel.getName());
            workoutDBtoUpdate.setDescription(updateWorkoutModel.getDescription());
            if(durationOfWorkout.contains(updateWorkoutModel.getDuration())){
            workoutDBtoUpdate.setDuration(updateWorkoutModel.getDuration());
            }else{
                throw new InvalidWorkoutBodyException();
            }
            workoutDBtoUpdate.setDifficulty(updateWorkoutModel.getDifficulty());
            WorkoutDB updatedWorkout = workoutsRepository.save(workoutDBtoUpdate);

            return new UpdateWorkoutModel(updatedWorkout.getId(), updatedWorkout.getName(), updatedWorkout.getDescription(), updatedWorkout.getDuration(), updatedWorkout.getDifficulty(), updatedWorkout.getUser());
        } else {
            throw new WorkoutNotFoundException();
        }
    }
    //delete workout
    public void deleteWorkout(String id) {
        if (workoutsRepository.findById(id).isPresent()) {
            workoutsRepository.deleteById(id);
        } else {
            throw new WorkoutNotFoundException();
        }
    }

}




















