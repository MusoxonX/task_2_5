package uz.pdp.task_2_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.task_2_5.entity.Tizim;
import uz.pdp.task_2_5.repository.TizimRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tizim")
public class TizimController {
    @Autowired
    TizimRepository tizimRepository;

    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getInfo(){
        List<Tizim> all = tizimRepository.findAll();
        return ResponseEntity.ok(all);
    }


}
