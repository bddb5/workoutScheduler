package com.schedule.workout.workoutScheduler.database;

import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.database.model.WorkoutDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface IWorkoutsRepository extends CrudRepository<WorkoutDB,String> {
    List<WorkoutDB> findAll();

    @Query("from WorkoutDB where name = ?1")
    List<WorkoutDB> findWorkoutsByName(String name);

    @Query("from WorkoutDB where description = ?1")
    List<WorkoutDB> findWorkoutsByDescription(String description);

    @Query("from WorkoutDB where duration = ?1")
    List<WorkoutDB> findWorkoutsByDuration(Integer duration);

    @Query("from WorkoutDB where difficulty = ?1")
    List<WorkoutDB> findWorkoutsByDifficulty(Integer difficulty);

}
