package uz.pdp.task_2_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_5.entity.User;
import uz.pdp.task_2_5.entity.q.RoleName;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.RegisterDto;
import uz.pdp.task_2_5.repository.RoleRepository;
import uz.pdp.task_2_5.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    RoleRepository roleRepository;

    public ApiResponse addManager(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository.findByEmail(registerDto.getEmail());
        if (optionalUser.isPresent()){
            return new ApiResponse("bunday username mavjud ",false);
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_MANAGER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("successfully registered",true);
    }


    public boolean sendEmail(String email,String emailCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Director@gmail.com");
            message.setTo(email);
            message.setSubject("Accountni tasdiqlash");
            message.setText("<a href='http://localhost:8081/api/verifyEmail'>Tasdiqlash</a>");
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
