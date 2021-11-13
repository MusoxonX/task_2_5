package uz.pdp.task_2_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_5.entity.Role;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.repository.RoleRepository;
import uz.pdp.task_2_5.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @PostMapping()
    public HttpEntity<?> addRole(@RequestBody Role roleDto){
        ApiResponse apiResponse = roleService.addRole(roleDto);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @GetMapping()
    public HttpEntity<?> getRoles(){
        List<Role> all = roleRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
