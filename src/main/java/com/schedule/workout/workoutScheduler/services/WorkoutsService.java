package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.controller.workout.CreateWorkoutModel;
import com.schedule.workout.workoutScheduler.controller.workout.UpdateWorkoutModel;
import com.schedule.workout.workoutScheduler.controller.workout.WorkoutModel;
import com.schedule.workout.workoutScheduler.database.IRolesRepository;
import com.schedule.workout.workoutScheduler.database.IUserRoleRepository;
import com.schedule.workout.workoutScheduler.database.IUsersRepository;
import com.schedule.workout.workoutScheduler.database.IWorkoutsRepository;
import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.database.model.UserRoleDB;
import com.schedule.workout.workoutScheduler.database.model.WorkoutDB;
import com.schedule.workout.workoutScheduler.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import static java.util.Arrays.asList;

@Service
public class WorkoutsService {

    @Autowired
    private IWorkoutsRepository workoutsRepository;
    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    IUserRoleRepository userRoleRepository;
    @Autowired
    IRolesRepository rolesRepository;

    //duration
    List<Integer> durationOfWorkout = asList(20, 30, 45, 60);

    //if user is in role of a trainer
    private boolean ifUserHasPermission(List<UserRoleDB> userRoles) {
        String targetRole = "Trainer";
        return userRoles.stream().anyMatch(userRoleDB -> targetRole.equals(userRoleDB.getRoleDB().getName()));
    }

    //create  workout
    public CreateWorkoutModel createWorkout(CreateWorkoutModel createWorkoutModel) {
        List<UserRoleDB> userRoles = userRoleRepository.findRolesByUserId(createWorkoutModel.getTrainerId());
        if (!ifUserHasPermission(userRoles)) {
            throw new AccessForbiddenException();
        }
        WorkoutDB workoutDB = new WorkoutDB();
        UserDB user = usersRepository.findById(createWorkoutModel.getTrainerId()).orElseThrow(UserNotFoundException::new);
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
        return new CreateWorkoutModel(workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(),
                workoutDB.getDifficulty(), workoutDB.getTrainerId());
    }

    //get all workouts/filters
    public List<WorkoutModel> getAllWorkouts(String name, Integer duration, Integer difficulty, String userId) {
        List<WorkoutModel> workoutList = new ArrayList<>();

        if (name != null || duration != null || difficulty != null || userId != null) {
            workoutsRepository.filterWorkouts(name, duration, difficulty, userId).forEach(workoutDB ->
                    workoutList.add(new WorkoutModel(workoutDB.getId(), workoutDB.getName(), workoutDB.getDescription(),
                            workoutDB.getDuration(), workoutDB.getDifficulty(), workoutDB.getTrainerId(), workoutDB.getUser())));
        } else {
            workoutsRepository.findAll().forEach(workoutDB -> workoutList.add(new WorkoutModel(workoutDB.getId(),
                    workoutDB.getName(), workoutDB.getDescription(), workoutDB.getDuration(), workoutDB.getDifficulty(),
                    workoutDB.getTrainerId(), workoutDB.getUser())));
        }
        return workoutList;
    }

    //get workout by id
    public WorkoutModel getWorkoutById(String id) {
        if (workoutsRepository.existsById(id)) {
            WorkoutDB workoutById = workoutsRepository.findById(id).get();
            return new WorkoutModel(workoutById.getId(), workoutById.getName(), workoutById.getDescription(),
                    workoutById.getDuration(), workoutById.getDifficulty(), workoutById.getTrainerId(), workoutById.getUser());
        } else {
            throw new WorkoutNotFoundException();
        }
    }

    //update workout
    public UpdateWorkoutModel updateWorkout(String id, UpdateWorkoutModel updateWorkoutModel) {
        List<UserRoleDB> userRoles = userRoleRepository.findRolesByUserId(updateWorkoutModel.getTrainerId());
        if (!ifUserHasPermission(userRoles)) {
            throw new AccessForbiddenException();
        }
        UserDB userDB = usersRepository.findById(updateWorkoutModel.getTrainerId()).orElse(null);
        if (workoutsRepository.existsById(id)) {
            WorkoutDB workoutDBtoUpdate = workoutsRepository.findById(id).get();
            workoutDBtoUpdate.setName(updateWorkoutModel.getName());
            workoutDBtoUpdate.setDescription(updateWorkoutModel.getDescription());

            if (durationOfWorkout.contains(updateWorkoutModel.getDuration())) {
                workoutDBtoUpdate.setDuration(updateWorkoutModel.getDuration());
            } else {
                throw new InvalidWorkoutBodyException();
            }
            workoutDBtoUpdate.setDifficulty(updateWorkoutModel.getDifficulty());
            workoutDBtoUpdate.setUser(userDB);
            WorkoutDB updatedWorkout = workoutsRepository.save(workoutDBtoUpdate);
            return new UpdateWorkoutModel(updatedWorkout.getName(), updatedWorkout.getDescription(),
                    updatedWorkout.getDuration(), updatedWorkout.getDifficulty(), updatedWorkout.getTrainerId());
        } else {
            throw new WorkoutNotFoundException();
        }
    }

    //delete workout
    public void deleteWorkout(String id) {
        if (workoutsRepository.existsById(id)) {
            workoutsRepository.deleteById(id);
        } else {
            throw new WorkoutNotFoundException();
        }
    }
}