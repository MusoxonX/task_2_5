package uz.pdp.task_2_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_5.entity.User;
import uz.pdp.task_2_5.entity.q.RoleName;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.LoginDto;
import uz.pdp.task_2_5.payload.RegisterDto;
import uz.pdp.task_2_5.repository.RoleRepository;
import uz.pdp.task_2_5.repository.UserRepository;
import uz.pdp.task_2_5.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse register(RegisterDto registerDto){
        boolean email = userRepository.existsByEmail(registerDto.getEmail());
        if (email){
            return new ApiResponse("like this email already exists",false);
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_XODIM)));

        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendEmail(user.getEmail(),user.getEmailCode());
        return new ApiResponse("muvaffaqiyatli ruyhatdan o'tdingiz",true);
    }

    public boolean sendEmail(String email,String emailCode){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("musoxo@gmail.com");
            message.setTo(email);
            message.setSubject("Accountni tasdiqlash");
            message.setText("<a href='http://localhost:8081/api/verifyEmail'>Tasdiqlash</a>");
            javaMailSender.send(message);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("account tasdiqlandi",true);
        }
        return new ApiResponse("account allaqachon tasdiqlangan",false);
    }


    public ApiResponse login(LoginDto loginDto) {
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));

            User user = (User) authenticate.getPrincipal();

            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());

            return new ApiResponse("Token",true,token);

        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("password or login is incorrect",false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()){
            optionalUser.get();
        }
        throw new UsernameNotFoundException(username+ " topilmadi");
    }
}
