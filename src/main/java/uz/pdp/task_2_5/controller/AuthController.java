package uz.pdp.task_2_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2_5.entity.User;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.LoginDto;
import uz.pdp.task_2_5.payload.RegisterDto;
import uz.pdp.task_2_5.repository.UserRepository;
import uz.pdp.task_2_5.service.AuthService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.register(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity.status(200).body("user deleted");
        }
        return ResponseEntity.status(409).body("user not found");
    }

//    ------------ xodimlar ro'yxatini olish-------------
    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping("/xodimlar")
    public HttpEntity<?> getXodimlar(){
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }

//---------------- xodimni idsi bo'yicha topish---------------
    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @GetMapping("/xodim/{xodimId}")
    public HttpEntity<?> getXodim(@PathVariable Integer xodimId){
        Optional<User> optionalUser = userRepository.findById(xodimId);
        return ResponseEntity.ok(optionalUser.get());
    }


    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String email,@RequestParam String emailCode){
        ApiResponse apiResponse = authService.verifyEmail(emailCode,email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }
}
