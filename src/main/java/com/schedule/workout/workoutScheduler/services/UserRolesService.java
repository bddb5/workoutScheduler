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

    public CreateUserRoleModel createUserRole(CreateUserRoleModel createUserRoleModel) {
        List<UserRoleDB> userRoles = userRoleRepository.findByUserIdOrRoleId(createUserRoleModel.getUserID(),createUserRoleModel.getRoleID());
        UserRoleDB userRoleDB = new UserRoleDB();
        UserDB userDB = usersRepository.findById(createUserRoleModel.getUserID()).orElse(null);
        RoleDB roleDB = rolesRepository.findById(createUserRoleModel.getRoleID()).orElse(null);
        if (userDB != null) {
            if (roleDB != null) {
                userRoleDB.setId(UUID.randomUUID().toString());

                userRoles.forEach(userRole -> {
                    String existingRole = userRole.getRoleID();
                    String existingUser = userRole.getUserID();
                    if (existingUser.equals(createUserRoleModel.getUserID()) &&
                            existingRole.equals(createUserRoleModel.getRoleID())) {
                        throw new UserRoleAlreadyExists();
                    }
                });
                userRoleDB.setUserDB(userDB);
                userRoleDB.setRoleDB(roleDB);
                userRoleDB.setCreatedOn(createUserRoleModel.getCreatedOn());
                userRoleRepository.save(userRoleDB);
                return new CreateUserRoleModel(userRoleDB.getUserID(), userRoleDB.getRoleID(), userRoleDB.getCreatedOn());
            } else {
                throw new RoleNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    public UpdateUserRoleModel updateUserRole(String id, UpdateUserRoleModel updateUserRoleModel) {
        UserDB userDB = usersRepository.findById(updateUserRoleModel.getUserID()).orElse(null);
        RoleDB roleDB = rolesRepository.findById(updateUserRoleModel.getRoleID()).orElse(null);
        if (userRoleRepository.existsById(id)) {
            UserRoleDB userRoleToUpdate = userRoleRepository.findById(id).get();
            if (userDB != null) {
                if (roleDB != null) {
                    userRoleToUpdate.setUserDB(userDB);
                    userRoleToUpdate.setRoleDB(roleDB);
                    userRoleToUpdate.setCreatedOn(updateUserRoleModel.getCreatedOn());
                    UserRoleDB updatedUserRole = userRoleRepository.save(userRoleToUpdate);
                    return new UpdateUserRoleModel(updatedUserRole.getUserID(), updatedUserRole.getRoleID(),
                            updatedUserRole.getCreatedOn());
                } else {
                    throw new RoleNotFoundException();
                }
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw new UserRoleNotFoundException();
        }
    }

    public UserRoleModel getUserRoleById(String id) {
        if (userRoleRepository.existsById(id)) {
            UserRoleDB userRoleDB = userRoleRepository.findById(id).get();
            return new UserRoleModel(userRoleDB.getId(), userRoleDB.getUserID(), userRoleDB.getRoleID(),
                    userRoleDB.getUserDB(), userRoleDB.getRoleDB(), userRoleDB.getCreatedOn());
        } else {
            throw new UserRoleNotFoundException();
        }
    }

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

    public void deleteUserRole(String id) {
        if (userRoleRepository.existsById(id)) {
            userRoleRepository.deleteById(id);
        } else {
            throw new UserRoleNotFoundException();
        }
    }
}
