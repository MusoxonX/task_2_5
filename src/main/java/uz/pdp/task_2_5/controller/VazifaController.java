package uz.pdp.task_2_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.VazifaDto;
import uz.pdp.task_2_5.repository.VazifaRepository;
import uz.pdp.task_2_5.service.VazifaService;


@RestController
@RequestMapping("/api/vazifa")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class VazifaController {
    @Autowired
    VazifaService vazifaService;

    @Autowired
    VazifaRepository vazifaRepository;

//    ----- bunga faqat director va manager qusha oladi-----------
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> addVazifa(@RequestBody VazifaDto vazifaDto){
        ApiResponse apiResponse = vazifaService.addVazifa(vazifaDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

// --------- bu yo'lga xodimlar murojat qiladi
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER','ROLE_XODIM')")
    @GetMapping("/xodim/{id}")
    public HttpEntity<?> getVazifa(@PathVariable Integer id){
        ApiResponse apiResponse = vazifaService.getVazifa(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER','ROLE_XODIM')")
    @PostMapping("/finished/{vazifaId}")
    public HttpEntity<?> addFinished(@PathVariable Integer vazifaId,@RequestBody boolean finished){
        ApiResponse apiResponse = vazifaService.addFinished(vazifaId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}