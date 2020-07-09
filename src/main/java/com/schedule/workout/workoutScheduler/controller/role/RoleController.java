package com.schedule.workout.workoutScheduler.controller.role;


import com.schedule.workout.workoutScheduler.controller.user.CreateUserModel;
import com.schedule.workout.workoutScheduler.controller.user.UpdateUserModel;
import com.schedule.workout.workoutScheduler.controller.user.UserModel;
import com.schedule.workout.workoutScheduler.exceptions.InvalidUserBodyException;
import com.schedule.workout.workoutScheduler.exceptions.RoleAlreadyExists;
import com.schedule.workout.workoutScheduler.exceptions.RoleNotFoundException;
import com.schedule.workout.workoutScheduler.exceptions.UserNotFoundException;
import com.schedule.workout.workoutScheduler.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    RolesService rolesService;

    @RequestMapping(method = RequestMethod.POST, value = "/roles")
    public ResponseEntity<CreateRoleModel> createRole(@Valid @RequestBody CreateRoleModel createRoleModel) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(rolesService.createRole(createRoleModel));

        } catch (RoleAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/roles/{id}")
    public ResponseEntity updateRole(@PathVariable String id, @Valid @RequestBody UpdateRoleModel updateRoleModel) {
        try {
            rolesService.updateRole(id, updateRoleModel);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/roles/{id}")
    public ResponseEntity<RoleModel> getRoleById(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(rolesService.getRoleById(id));
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/roles")
    public ResponseEntity<List<RoleModel>> getAllRoles(@RequestParam(name = "name", required = false) String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rolesService.getAllRoles(name));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/roles/{id}")
    public ResponseEntity deleteRole(@PathVariable String id) {
        try {
            rolesService.deleteRole(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
