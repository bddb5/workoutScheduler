package com.schedule.workout.workoutScheduler.database;

import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.database.model.WorkoutDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IWorkoutsRepository extends CrudRepository<WorkoutDB,String> {
    List<WorkoutDB> findAll();

    @Query("from WorkoutDB wdb where wdb.name = :name or wdb.duration = :duration or wdb.difficulty = :difficulty or wdb.user.id = :userId or wdb.name = :name and wdb.duration = :duration and wdb.user.id = :userId")
    List<WorkoutDB> filterWorkouts(String name, Integer duration, Integer difficulty, String userId);


}
