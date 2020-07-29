package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.controller.user.CreateUserModel;
import com.schedule.workout.workoutScheduler.controller.user.UpdateUserModel;
import com.schedule.workout.workoutScheduler.controller.user.UserModel;
import com.schedule.workout.workoutScheduler.database.IUsersRepository;
import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private IUsersRepository usersRepository;

    //create user
    public CreateUserModel createUser(CreateUserModel createUserModel) {
        UserDB userDB = new UserDB();
        userDB.setId(UUID.randomUUID().toString());
        userDB.setFirstName(createUserModel.getFirstName());
        userDB.setLastName(createUserModel.getLastName());
        userDB.setAge(createUserModel.getAge());
        userDB.setEmail(createUserModel.getEmail());
        userDB.setPhoneNumber(createUserModel.getPhoneNumber());
        usersRepository.save(userDB);

        return new CreateUserModel(userDB.getId(), userDB.getFirstName(), userDB.getLastName(), userDB.getAge(),
                userDB.getEmail(), userDB.getPhoneNumber());
    }

    //update user
    public UpdateUserModel updateUser(String id, UpdateUserModel updateUserModel) {
        if (usersRepository.existsById(id)) {
            UserDB userDBtoUpdate = usersRepository.findById(id).get();

            userDBtoUpdate.setFirstName(updateUserModel.getFirstName());
            userDBtoUpdate.setLastName(updateUserModel.getLastName());
            userDBtoUpdate.setAge(updateUserModel.getAge());
            userDBtoUpdate.setEmail(updateUserModel.getEmail());
            userDBtoUpdate.setPhoneNumber(updateUserModel.getPhoneNumber());
            UserDB updatedUser = usersRepository.save(userDBtoUpdate);

            return new UpdateUserModel(updatedUser.getId(), updatedUser.getFirstName(), updatedUser.getLastName(),
                    updatedUser.getAge(), updatedUser.getEmail(), updatedUser.getPhoneNumber());
        } else {
            throw new UserNotFoundException();
        }
    }

    //list of all users/filters
    public List<UserModel> getAllUsers(String firstName, String lastName) {
        List<UserModel> userList = new ArrayList<>();

        if (firstName != null || lastName != null) {
            usersRepository.filterUsersByFirstNameAndLastName(firstName, lastName).forEach(userDB -> userList.add
                    (new UserModel(userDB.getId(), userDB.getFirstName(), userDB.getLastName(), userDB.getAge(),
                            userDB.getEmail(), userDB.getPhoneNumber())));
        } else {
            usersRepository.findAll().forEach(userDB -> userList.add(new UserModel(userDB.getId(), userDB.getFirstName(),
                    userDB.getLastName(), userDB.getAge(), userDB.getEmail(), userDB.getPhoneNumber())));
        }
        return userList;
    }

    //get user by id
    public UserModel getUserById(String id) {
        if (usersRepository.existsById(id)) {
            UserDB userById = usersRepository.findById(id).get();
            return new UserModel(userById.getId(), userById.getFirstName(), userById.getLastName(), userById.getAge(),
                    userById.getEmail(), userById.getPhoneNumber());
        } else {
            throw new UserNotFoundException();
        }
    }

    //delete user by id
    public void deleteUser(String id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }
}