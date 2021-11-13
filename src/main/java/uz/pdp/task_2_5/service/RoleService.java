package uz.pdp.task_2_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_5.entity.Role;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public ApiResponse addRole(Role roleDto){
        roleRepository.save(roleDto);
        return new ApiResponse("role added",true);
    }
}
