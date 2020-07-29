package com.schedule.workout.workoutScheduler.database;

import com.schedule.workout.workoutScheduler.database.model.UserRoleDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IUserRoleRepository extends CrudRepository<UserRoleDB,String> {
    List<UserRoleDB> findAll();
    @Query("from UserRoleDB urdb where urdb.userDB.id = :userID or urdb.roleDB.id = :roleID")
    List<UserRoleDB> findByUserIdOrRoleId(String userID,String roleID);
    @Query("from UserRoleDB urdb where urdb.userDB.id = :userID")
    List<UserRoleDB> findRolesByUserId(String userID);

}
