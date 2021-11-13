package uz.pdp.task_2_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.TurniketDto;
import uz.pdp.task_2_5.service.TurniketService;

@RestController
@RequestMapping("/api/turniket")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TurniketController {
    @Autowired
    TurniketService turniketService;

//-----------turniket qo'shish uchun---------
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> addTurniket(@RequestBody TurniketDto turniketDto){
        ApiResponse apiResponse = turniketService.addTurniket(turniketDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
//----------- xodimlar kelganini bildiradi-------
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PutMapping("/turniketKeldi/{id}")
    public HttpEntity<?> turniketKeldi(@PathVariable Integer id,@RequestBody TurniketDto turniketDto){
        ApiResponse apiResponse = turniketService.turniketKeldi(id,turniketDto);
        return ResponseEntity.ok(apiResponse);
    }
//    -------------xodimlar ketganini bildiradi----------------
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PutMapping("/turniketKetdi/{id}")
    public HttpEntity<?> turniketKetdi(@PathVariable Integer id,@RequestBody TurniketDto turniketDto){
        ApiResponse apiResponse = turniketService.turniketKetdi(id,turniketDto);
        return ResponseEntity.ok(apiResponse);
    }

}
