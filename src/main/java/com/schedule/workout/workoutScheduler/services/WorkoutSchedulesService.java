package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.controller.workoutSchedule.CreateWorkoutScheduleModel;
import com.schedule.workout.workoutScheduler.controller.workoutSchedule.UpdateWorkoutScheduleModel;
import com.schedule.workout.workoutScheduler.controller.workoutSchedule.WorkoutScheduleModel;
import com.schedule.workout.workoutScheduler.database.IWorkoutSchedulesRepository;
import com.schedule.workout.workoutScheduler.database.IWorkoutsRepository;
import com.schedule.workout.workoutScheduler.database.model.WorkoutDB;
import com.schedule.workout.workoutScheduler.database.model.WorkoutScheduleDB;
import com.schedule.workout.workoutScheduler.exceptions.InvalidWorkoutScheduleBodyException;
import com.schedule.workout.workoutScheduler.exceptions.WorkoutNotFoundException;
import com.schedule.workout.workoutScheduler.exceptions.WorkoutScheduleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkoutSchedulesService {
    @Autowired
    IWorkoutSchedulesRepository workoutSchedulesRepository;
    @Autowired
    IWorkoutsRepository workoutsRepository;

    //create schedule
    public CreateWorkoutScheduleModel createSchedule(CreateWorkoutScheduleModel createWorkoutScheduleModel) {
        List<WorkoutScheduleDB> schedulesByDay = workoutSchedulesRepository.findByDay(createWorkoutScheduleModel.getDay());
        WorkoutScheduleDB workoutScheduleDB = new WorkoutScheduleDB();
        WorkoutDB workoutDB = workoutsRepository.findById(createWorkoutScheduleModel.getWorkoutID()).orElse(null);
        if (workoutDB != null) {
            long newWorkoutStartTimeMillis = createWorkoutScheduleModel.getStartWorkout().getTime();
            long newWorkoutEndTimeMillis = newWorkoutStartTimeMillis + (workoutDB.getDuration() * 60 * 1000);

            schedulesByDay.forEach(schedule -> {
                long startWorkoutMillis = schedule.getStartWorkout().getTime();
                long endWorkoutMillis = startWorkoutMillis + (schedule.getWorkoutDB().getDuration() * 60 * 1000);
                if ((newWorkoutStartTimeMillis >= startWorkoutMillis && newWorkoutStartTimeMillis < endWorkoutMillis) ||
                        (newWorkoutEndTimeMillis > startWorkoutMillis && newWorkoutEndTimeMillis <= endWorkoutMillis) ||
                        (newWorkoutStartTimeMillis < startWorkoutMillis && newWorkoutEndTimeMillis > endWorkoutMillis)) {
                    throw new InvalidWorkoutScheduleBodyException();
                }
            });
            workoutScheduleDB.setId(UUID.randomUUID().toString());
            workoutScheduleDB.setDay(createWorkoutScheduleModel.getDay());
            workoutScheduleDB.setStartWorkout(createWorkoutScheduleModel.getStartWorkout());
            workoutScheduleDB.setWorkoutDB(workoutDB);
            workoutSchedulesRepository.save(workoutScheduleDB);

            return new CreateWorkoutScheduleModel(workoutScheduleDB.getDay(), workoutScheduleDB.getStartWorkout(),
                    workoutScheduleDB.getWorkoutId());
        } else {
            throw new WorkoutNotFoundException();
        }
    }

    //update schedule
    public UpdateWorkoutScheduleModel updateSchedule(String id, UpdateWorkoutScheduleModel updateWorkoutScheduleModel) {
        List<WorkoutScheduleDB> schedulesByDay = workoutSchedulesRepository.findByDay(updateWorkoutScheduleModel.getDay());
        WorkoutDB workoutDB = workoutsRepository.findById(updateWorkoutScheduleModel.getWorkoutID()).orElse(null);

        if (workoutSchedulesRepository.existsById(id)) {
            if (workoutDB != null) {
                WorkoutScheduleDB workoutScheduleDBToUpdate = workoutSchedulesRepository.findById(id).get();
                workoutScheduleDBToUpdate.setDay(updateWorkoutScheduleModel.getDay());
                workoutScheduleDBToUpdate.setStartWorkout(updateWorkoutScheduleModel.getStartWorkout());
                workoutScheduleDBToUpdate.setWorkoutDB(workoutDB);
                WorkoutScheduleDB updatedWorkoutSchedule = workoutSchedulesRepository.save(workoutScheduleDBToUpdate);

                long newWorkoutStartTimeMillis = updateWorkoutScheduleModel.getStartWorkout().getTime();
                long newWorkoutEndTimeMillis = newWorkoutStartTimeMillis + (workoutDB.getDuration() * 60 * 1000);

                schedulesByDay.forEach(schedule -> {
                    long startWorkoutMillis = schedule.getStartWorkout().getTime();
                    long endWorkoutMillis = startWorkoutMillis + (schedule.getWorkoutDB().getDuration() * 60 * 1000);


                    if ((newWorkoutStartTimeMillis >= startWorkoutMillis && newWorkoutStartTimeMillis < endWorkoutMillis) ||
                            (newWorkoutEndTimeMillis > startWorkoutMillis && newWorkoutEndTimeMillis <= endWorkoutMillis) ||
                            (newWorkoutStartTimeMillis < startWorkoutMillis && newWorkoutEndTimeMillis > endWorkoutMillis)) {
                        throw new InvalidWorkoutScheduleBodyException();
                    }
                });
                return new UpdateWorkoutScheduleModel(updatedWorkoutSchedule.getDay(),
                        updatedWorkoutSchedule.getStartWorkout(), updatedWorkoutSchedule.getWorkoutId());
            } else {
                throw new WorkoutNotFoundException();
            }
        } else {
            throw new WorkoutScheduleNotFoundException();
        }
    }

    // get schedule by id
    public WorkoutScheduleModel getScheduleById(String id) {
        if (workoutSchedulesRepository.existsById(id)) {
            WorkoutScheduleDB workoutScheduleById = workoutSchedulesRepository.findById(id).get();
            return new WorkoutScheduleModel(workoutScheduleById.getId(), workoutScheduleById.getDay(),
                    workoutScheduleById.getStartWorkout(), workoutScheduleById.getWorkoutId());
        } else {
            throw new WorkoutScheduleNotFoundException();
        }
    }

    //get all schedules
    public List<WorkoutScheduleModel> getAllSchedules(Integer day, String workoutId) {
        List<WorkoutScheduleModel> scheduleList = new ArrayList<>();
        if (day != null || workoutId != null) {
            workoutSchedulesRepository.filterSchedules(day, workoutId).forEach(workoutScheduleDB -> scheduleList.add
                    (new WorkoutScheduleModel(workoutScheduleDB.getId(), workoutScheduleDB.getDay(),
                            workoutScheduleDB.getStartWorkout(), workoutScheduleDB.getWorkoutId())));
        } else {
            workoutSchedulesRepository.findAll().forEach(workoutScheduleDB -> scheduleList.add
                    (new WorkoutScheduleModel(workoutScheduleDB.getId(), workoutScheduleDB.getDay(),
                            workoutScheduleDB.getStartWorkout()
                            , workoutScheduleDB.getWorkoutId())));
        }
        return scheduleList;
    }

    //delete schedule
    public void deleteSchedule(String id) {
        if (workoutSchedulesRepository.existsById(id)) {
            workoutSchedulesRepository.deleteById(id);
        } else {
            throw new WorkoutScheduleNotFoundException();
        }
    }
}
