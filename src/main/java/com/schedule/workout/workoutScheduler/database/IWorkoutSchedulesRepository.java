package com.schedule.workout.workoutScheduler.database;

import com.schedule.workout.workoutScheduler.database.model.WorkoutScheduleDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWorkoutSchedulesRepository extends CrudRepository<WorkoutScheduleDB,String> {
    List<WorkoutScheduleDB> findAll();
    @Query("from WorkoutScheduleDB wsdb where wsdb.day = :day or wsdb.workoutDB.id = :workoutId")
    List<WorkoutScheduleDB> filterSchedules(String day,String workoutId);
}
