package com.schedule.workout.workoutScheduler.database;

import com.schedule.workout.workoutScheduler.database.model.WorkoutScheduleDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface IWorkoutSchedulesRepository extends CrudRepository<WorkoutScheduleDB,String> {
    List<WorkoutScheduleDB> findAll();

    @Query("from WorkoutScheduleDB wsdb where wsdb.day = :day or wsdb.workoutDB.id = :workoutId")
    List<WorkoutScheduleDB> filterSchedules(Integer day,String workoutId);
    @Query("from WorkoutScheduleDB wsdb where wsdb.day = :day")
    List<WorkoutScheduleDB> findByDay(Integer day);



}
