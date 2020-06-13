package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.controller.workoutSchedule.CreateWorkoutScheduleModel;
import com.schedule.workout.workoutScheduler.controller.workoutSchedule.UpdateWorkoutScheduleModel;
import com.schedule.workout.workoutScheduler.controller.workoutSchedule.WorkoutScheduleModel;
import com.schedule.workout.workoutScheduler.database.IWorkoutSchedulesRepository;
import com.schedule.workout.workoutScheduler.database.IWorkoutsRepository;
import com.schedule.workout.workoutScheduler.database.model.WorkoutDB;
import com.schedule.workout.workoutScheduler.database.model.WorkoutScheduleDB;
import com.schedule.workout.workoutScheduler.exceptions.InvalidWorkoutBodyException;
import com.schedule.workout.workoutScheduler.exceptions.InvalidWorkoutScheduleBodyException;
import com.schedule.workout.workoutScheduler.exceptions.WorkoutScheduleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class WorkoutSchedulesService {
    @Autowired
    IWorkoutSchedulesRepository workoutSchedulesRepository;
    @Autowired
    IWorkoutsRepository workoutsRepository;

    //create schedule
    public CreateWorkoutScheduleModel createSchedule(CreateWorkoutScheduleModel createWorkoutScheduleModel) {
        WorkoutScheduleDB workoutScheduleDB = new WorkoutScheduleDB();
        WorkoutDB workoutDB = workoutsRepository.findById(createWorkoutScheduleModel.getWorkoutID()).orElse(null);
        workoutScheduleDB.setId(UUID.randomUUID().toString());
        workoutScheduleDB.setDay(createWorkoutScheduleModel.getDay());
        workoutScheduleDB.setStartWorkout(createWorkoutScheduleModel.getStartWorkout());
        workoutScheduleDB.setEndWorkout(createWorkoutScheduleModel.getEndWorkout());
        //missing day validation first,but not the point doesn't work anyway
        if (workoutScheduleDB.getStartWorkout().before(createWorkoutScheduleModel.getEndWorkout()) &&
                workoutScheduleDB.getEndWorkout().after(createWorkoutScheduleModel.getStartWorkout())) {
            throw new InvalidWorkoutScheduleBodyException();
        }
            workoutScheduleDB.setWorkoutDB(workoutDB);
            workoutSchedulesRepository.save(workoutScheduleDB);
            return new CreateWorkoutScheduleModel(workoutScheduleDB.getDay(), workoutScheduleDB.getStartWorkout(),
                    workoutScheduleDB.getEndWorkout(), workoutScheduleDB.getWorkoutId());

    }
    //update schedule
    public UpdateWorkoutScheduleModel updateSchedule(String id,UpdateWorkoutScheduleModel updateWorkoutScheduleModel){
        WorkoutDB workoutDB = workoutsRepository.findById(updateWorkoutScheduleModel.getWorkoutID()).orElse(null);
        if(workoutSchedulesRepository.existsById(id)){
            WorkoutScheduleDB workoutScheduleDBToUpdate = workoutSchedulesRepository.findById(id).get();
            workoutScheduleDBToUpdate.setDay(updateWorkoutScheduleModel.getDay());
            workoutScheduleDBToUpdate.setStartWorkout(updateWorkoutScheduleModel.getStartWorkout());
            workoutScheduleDBToUpdate.setEndWorkout(updateWorkoutScheduleModel.getEndWorkout());
            workoutScheduleDBToUpdate.setWorkoutDB(workoutDB);
            WorkoutScheduleDB updatedWorkoutSchedule = workoutSchedulesRepository.save(workoutScheduleDBToUpdate);
            return new UpdateWorkoutScheduleModel(updatedWorkoutSchedule.getDay(),updatedWorkoutSchedule.getStartWorkout(),
                    updatedWorkoutSchedule.getEndWorkout(),updatedWorkoutSchedule.getWorkoutId());
        }
        else {
            throw new WorkoutScheduleNotFoundException();
        }
    }
    // get schedule by id
    public WorkoutScheduleModel getScheduleById(String id){
        if(workoutSchedulesRepository.existsById(id)){
            WorkoutScheduleDB workoutScheduleById = workoutSchedulesRepository.findById(id).get();
            return new WorkoutScheduleModel(workoutScheduleById.getId(),workoutScheduleById.getDay(),workoutScheduleById.getStartWorkout(),
                    workoutScheduleById.getEndWorkout(),workoutScheduleById.getWorkoutId());
        }else{
            throw new WorkoutScheduleNotFoundException();
        }
    }
    //get all schedules
    public List<WorkoutScheduleModel> getAllSchedules(String day,String workoutId){
        List<WorkoutScheduleModel>  scheduleList = new ArrayList<>();
        if(day != null || workoutId != null){
            workoutSchedulesRepository.filterSchedules(day,workoutId).forEach(workoutScheduleDB -> scheduleList.add
                    (new WorkoutScheduleModel(workoutScheduleDB.getId(), workoutScheduleDB.getDay(), workoutScheduleDB.getStartWorkout(),
                            workoutScheduleDB.getEndWorkout(), workoutScheduleDB.getWorkoutId())));
        }else {
            workoutSchedulesRepository.findAll().forEach(workoutScheduleDB -> scheduleList.add
                    (new WorkoutScheduleModel(workoutScheduleDB.getId(), workoutScheduleDB.getDay(), workoutScheduleDB.getStartWorkout(),
                            workoutScheduleDB.getEndWorkout(), workoutScheduleDB.getWorkoutId())));
        }
        return scheduleList;
    }
    //delete schedule
    public void deleteSchedule(String id){
        if(workoutSchedulesRepository.existsById(id)){
            workoutSchedulesRepository.deleteById(id);
        }else{
            throw new WorkoutScheduleNotFoundException();
        }
    }
}
