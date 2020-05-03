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
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private IUsersRepository usersRepository;

    //create user
    public UserDB createUser(CreateUserModel createUserModel) {
        UserDB userDB = new UserDB();
        userDB.setId(UUID.randomUUID().toString());
        userDB.setFirstName(createUserModel.getFirstName());
        userDB.setLastName(createUserModel.getLastName());
        userDB.setAge(createUserModel.getAge());
        userDB.setEmail(createUserModel.getEmail());
        userDB.setPhoneNumber(createUserModel.getPhoneNumber());
        return usersRepository.save(userDB);
    }
    //update user
    public UserDB updateUser(String id, UpdateUserModel updateUserModel) {
        Optional<UserDB> user = usersRepository.findById(id);
        if (user.isPresent()) {

            UserDB userToUpdate = user.get();
            userToUpdate.setFirstName(updateUserModel.getFirstName());
            userToUpdate.setLastName(updateUserModel.getLastName());
            userToUpdate.setAge(updateUserModel.getAge());
            userToUpdate.setEmail(updateUserModel.getEmail());
            userToUpdate.setPhoneNumber(updateUserModel.getPhoneNumber());
            return usersRepository.save(userToUpdate);
        } else {
            throw new UserNotFoundException();
        }
    }
    //list of all users/filters
    public List<UserDB> getAllUsers(String firstName,String lastName) {
        List<UserDB> userList = new ArrayList<>();

        if(firstName != null ||lastName != null ){
            usersRepository.filterUsersByFirstNameAndLastName(firstName,lastName).forEach(userModel -> userList.add(new UserDB(userModel.getId(), userModel.getFirstName(), userModel.getLastName(), userModel.getAge(), userModel.getEmail(), userModel.getPhoneNumber())));
        }else{
            usersRepository.findAll().forEach(userModel -> userList.add(new UserDB(userModel.getId(), userModel.getFirstName(), userModel.getLastName(), userModel.getAge(), userModel.getEmail(), userModel.getPhoneNumber())));
        }
        return userList;
    }
    //get user by id
    public UserDB getUserById(String id){
        if(usersRepository.findById(id).isPresent()){
          UserDB userById = usersRepository.findById(id).get();
          return new UserDB(userById.getId(),userById.getFirstName(),userById.getLastName(),userById.getAge(),userById.getEmail(),userById.getPhoneNumber());
        } else {
            throw new UserNotFoundException();
        }
    }
    //delete user by id
    public void deleteUser(String id) {
        if (usersRepository.findById(id).isPresent()) {
            usersRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }
}






