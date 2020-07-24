package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.controller.userRole.CreateUserRoleModel;
import com.schedule.workout.workoutScheduler.controller.userRole.UpdateUserRoleModel;
import com.schedule.workout.workoutScheduler.controller.userRole.UserRoleModel;
import com.schedule.workout.workoutScheduler.database.IUsersRepository;
import com.schedule.workout.workoutScheduler.database.IRolesRepository;
import com.schedule.workout.workoutScheduler.database.IUserRoleRepository;
import com.schedule.workout.workoutScheduler.database.model.RoleDB;
import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.database.model.UserRoleDB;
import com.schedule.workout.workoutScheduler.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserRolesService {

    @Autowired
    IUsersRepository usersRepository;
    @Autowired
    IRolesRepository rolesRepository;
    @Autowired
    IUserRoleRepository userRoleRepository;

    //create
    public CreateUserRoleModel createUserRole(CreateUserRoleModel createUserRoleModel) {
        UserRoleDB userRoleDB = new UserRoleDB();
        UserDB userDB = usersRepository.findById(createUserRoleModel.getUserID()).orElseThrow(UserNotFoundException::new);
        RoleDB roleDB = rolesRepository.findById(createUserRoleModel.getRoleID()).orElseThrow(RoleNotFoundException::new);
        List<UserRoleDB> userRoles = userRoleRepository.findRolesByUserId(createUserRoleModel.getUserID());
        for (UserRoleDB userRole : userRoles) {
            if (userRole.getUserID().equals(createUserRoleModel.getUserID()) && userRole.getRoleID().equals(createUserRoleModel.getRoleID())) {
                throw new UserRoleAlreadyExists();
            }
        }
        userRoleDB.setId(UUID.randomUUID().toString());
        userRoleDB.setUserDB(userDB);
        userRoleDB.setRoleDB(roleDB);
        userRoleDB.setCreatedOn(createUserRoleModel.getCreatedOn());
        userRoleRepository.save(userRoleDB);
        return new CreateUserRoleModel(userRoleDB.getUserID(), userRoleDB.getRoleID(), userRoleDB.getCreatedOn());
    }
    //update
    public UpdateUserRoleModel updateUserRole(String id, UpdateUserRoleModel updateUserRoleModel) {
        List<UserRoleDB> userRoles = userRoleRepository.findRolesByUserId(updateUserRoleModel.getUserID());
        for (UserRoleDB userRole : userRoles) {
            if (userRole.getUserID().equals(updateUserRoleModel.getUserID()) && userRole.getRoleID().equals(updateUserRoleModel.getRoleID())) {
                throw new UserRoleAlreadyExists();
            }
        }
        UserDB userDB = usersRepository.findById(updateUserRoleModel.getUserID()).orElseThrow(UserNotFoundException::new);
        RoleDB roleDB = rolesRepository.findById(updateUserRoleModel.getRoleID()).orElseThrow(RoleNotFoundException::new);
        if (userRoleRepository.existsById(id)) {
            UserRoleDB userRoleToUpdate = userRoleRepository.findById(id).get();
            userRoleToUpdate.setUserDB(userDB);
            userRoleToUpdate.setRoleDB(roleDB);
            userRoleToUpdate.setCreatedOn(updateUserRoleModel.getCreatedOn());
            UserRoleDB updatedUserRole = userRoleRepository.save(userRoleToUpdate);
            return new UpdateUserRoleModel(updatedUserRole.getUserID(), updatedUserRole.getRoleID(),
                    updatedUserRole.getCreatedOn());
        } else {
            throw new UserRoleNotFoundException();
        }
    }
    //by id
    public UserRoleModel getUserRoleById(String id) {
        if (userRoleRepository.existsById(id)) {
            UserRoleDB userRoleDB = userRoleRepository.findById(id).get();
            return new UserRoleModel(userRoleDB.getId(), userRoleDB.getUserID(), userRoleDB.getRoleID(),
                    userRoleDB.getUserDB(), userRoleDB.getRoleDB(), userRoleDB.getCreatedOn());
        } else {
            throw new UserRoleNotFoundException();
        }
    }
    //get all
    public List<UserRoleModel> getAllUserRoles(String userID, String roleID) {
        List<UserRoleModel> userRoleList = new ArrayList<>();
        if (userID != null || roleID != null) {
            userRoleRepository.findByUserIdOrRoleId(userID, roleID).forEach(userRoleDB -> userRoleList.add
                    (new UserRoleModel(userRoleDB.getId(), userRoleDB.getUserID(), userRoleDB.getRoleID(),
                            userRoleDB.getUserDB(), userRoleDB.getRoleDB(), userRoleDB.getCreatedOn())));
        } else {
            userRoleRepository.findAll().forEach(userRoleDB -> userRoleList.add
                    (new UserRoleModel(userRoleDB.getId(), userRoleDB.getUserID(), userRoleDB.getRoleID(),
                            userRoleDB.getUserDB(), userRoleDB.getRoleDB(), userRoleDB.getCreatedOn())));

        }
        return userRoleList;
    }
    //delete
    public void deleteUserRole(String id) {
        if (userRoleRepository.existsById(id)) {
            userRoleRepository.deleteById(id);
        } else {
            throw new UserRoleNotFoundException();
        }
    }
}
