package com.schedule.workout.workoutScheduler.database;

import com.schedule.workout.workoutScheduler.database.model.UserDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IUsersRepository extends CrudRepository<UserDB, String> {
    List<UserDB> findAll();

    @Query("from UserDB udb where udb.firstName = :firstName or udb.lastName = :lastName")
    List<UserDB> filterUsersByFirstNameAndLastName(String firstName, String lastName);

}
