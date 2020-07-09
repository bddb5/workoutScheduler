package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.controller.role.CreateRoleModel;
import com.schedule.workout.workoutScheduler.controller.role.RoleModel;
import com.schedule.workout.workoutScheduler.controller.role.UpdateRoleModel;
import com.schedule.workout.workoutScheduler.database.IRolesRepository;
import com.schedule.workout.workoutScheduler.database.model.RoleDB;
import com.schedule.workout.workoutScheduler.exceptions.RoleAlreadyExists;
import com.schedule.workout.workoutScheduler.exceptions.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RolesService {

    @Autowired
    IRolesRepository rolesRepository;


    public CreateRoleModel createRole(CreateRoleModel createRoleModel) {
        List<RoleDB> roles = rolesRepository.findByName(createRoleModel.getName());
        RoleDB roleDB = new RoleDB();
        roleDB.setId(UUID.randomUUID().toString());
        roles.forEach(role -> {
            String existingRoleName = role.getName();
            if (existingRoleName.equals(createRoleModel.getName())) {
                throw new RoleAlreadyExists();
            }

        });
        roleDB.setName(createRoleModel.getName());
        roleDB.setDescription(createRoleModel.getDescription());
        rolesRepository.save(roleDB);
        return new CreateRoleModel(createRoleModel.getName(), createRoleModel.getDescription());
    }

    public UpdateRoleModel updateRole(String id, UpdateRoleModel updateRoleModel) {
        if (rolesRepository.existsById(id)) {
            RoleDB roleDBtoUpdate = rolesRepository.findById(id).get();

            roleDBtoUpdate.setName(updateRoleModel.getName());
            roleDBtoUpdate.setDescription(updateRoleModel.getDescription());
            RoleDB updatedRole = rolesRepository.save(roleDBtoUpdate);

            return new UpdateRoleModel(updatedRole.getName(), updatedRole.getDescription());
        } else {
            throw new RoleNotFoundException();
        }
    }

    public RoleModel getRoleById(String id) {
        if (rolesRepository.existsById(id)) {
            RoleDB roleById = rolesRepository.findById(id).get();
            return new RoleModel(roleById.getId(), roleById.getName(), roleById.getDescription());
        } else {
            throw new RoleNotFoundException();
        }
    }

    public List<RoleModel> getAllRoles(String name){
        List<RoleModel> roleList = new ArrayList<>();
        if(name != null){
            rolesRepository.findByName(name).forEach(roleDB -> roleList.add(new RoleModel(roleDB.getId(),roleDB.getName(),roleDB.getDescription())));
        }else {
            rolesRepository.findAll().forEach(roleDB -> roleList.add(new RoleModel(roleDB.getId(), roleDB.getName(), roleDB.getDescription())));
        }
        return roleList;
    }
    //delete role
    public void deleteRole(String id){
        if(rolesRepository.existsById(id)){
            rolesRepository.deleteById(id);
        }else{
            throw new RoleNotFoundException();
        }
    }



}




