package uz.pdp.task_2_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.RegisterDto;
import uz.pdp.task_2_5.service.ManagerService;

@RestController
@RequestMapping("/api/manager")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @PostMapping()
    public HttpEntity<?> addManager(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = managerService.addManager(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }
}
