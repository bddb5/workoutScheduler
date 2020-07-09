package com.schedule.workout.workoutScheduler.controller.userRole;

import com.schedule.workout.workoutScheduler.exceptions.*;
import com.schedule.workout.workoutScheduler.services.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserRoleController {

    @Autowired
    UserRolesService userRolesService;

    @RequestMapping(method = RequestMethod.POST, value = "/userRoles")
    public ResponseEntity<CreateUserRoleModel> createUserRole(@Valid @RequestBody CreateUserRoleModel createUserRoleModel) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userRolesService.createUserRole(createUserRoleModel));

        } catch (UserNotFoundException | RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (UserRoleAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/userRoles/{id}")
    public ResponseEntity updateUserRole(@PathVariable String id, @Valid @RequestBody UpdateUserRoleModel updateUserRoleModel) {
        try {
            userRolesService.updateUserRole(id, updateUserRoleModel);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        } catch (UserRoleNotFoundException | UserNotFoundException | RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userRoles")
    public ResponseEntity<List<UserRoleModel>> getAllUserRoles(@RequestParam(name = "userID", required = false) String userID,
                                                               @RequestParam(name = "roleID", required = false) String roleID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userRolesService.getAllUserRoles(userID, roleID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userRoles/{id}")
    public ResponseEntity<UserRoleModel> getUserRoleById(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userRolesService.getUserRoleById(id));
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/userRoles/{id}")
    public ResponseEntity deleteUserRoles(@PathVariable String id) {
        try {
            userRolesService.deleteUserRole(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }


}
