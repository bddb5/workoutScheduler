package com.schedule.workout.workoutScheduler.database;

import com.schedule.workout.workoutScheduler.database.model.RoleDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IRolesRepository extends CrudRepository<RoleDB,String> {
    List<RoleDB> findAll();
}
