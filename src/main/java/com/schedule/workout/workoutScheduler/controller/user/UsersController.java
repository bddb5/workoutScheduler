package com.schedule.workout.workoutScheduler.controller.user;


import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.exceptions.InvalidUserBodyException;
import com.schedule.workout.workoutScheduler.exceptions.UserNotFoundException;
import com.schedule.workout.workoutScheduler.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;


    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public ResponseEntity<UserDB> createUser(@Valid @RequestBody CreateUserModel createUserModel) {
            try {
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(usersService.createUser(createUserModel));
            }catch (InvalidUserBodyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public ResponseEntity<UserDB> updateUser(@PathVariable String id,@Valid @RequestBody UpdateUserModel updateUserModel) {
        try {
            usersService.updateUser(id,updateUserModel);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }catch (InvalidUserBodyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public ResponseEntity<UserDB> getUserById(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usersService.getUserById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ResponseEntity<List<UserDB>> getAllUsers(@RequestParam(name = "firstName", required = false) String firstName,
                                                    @RequestParam(name = "lastName", required = false) String lastName)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.getAllUsers(firstName,lastName));

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public ResponseEntity<UserDB> deleteUser(@PathVariable String id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

}


