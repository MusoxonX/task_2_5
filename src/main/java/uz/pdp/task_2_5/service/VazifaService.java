package uz.pdp.task_2_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_5.entity.User;
import uz.pdp.task_2_5.entity.Vazifa;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.VazifaDto;
import uz.pdp.task_2_5.repository.UserRepository;
import uz.pdp.task_2_5.repository.VazifaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VazifaService {
    @Autowired
    VazifaRepository vazifaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public ApiResponse addVazifa(VazifaDto vazifaDto){
        Vazifa vazifa = new Vazifa();
        vazifa.setName(vazifaDto.getName());
        vazifa.setDiscription(vazifaDto.getDiscription());
        vazifa.setGivenTimeToFinish(vazifaDto.getGivenTimeToFinish());
        Optional<User> optionalUser = userRepository.findById(vazifaDto.getManagerId());
        if (!optionalUser.isPresent()){
            return new ApiResponse("manager not found",false);
        }

        vazifa.setManager(optionalUser.get());

        Optional<User> optionalUser1 = userRepository.findById(vazifaDto.getXodimId());
        if (!optionalUser1.isPresent()){
            return new ApiResponse("xodim topilmadim",false);
        }
        User xodim = optionalUser1.get();
        vazifa.setXodim(xodim);
        vazifaRepository.save(vazifa);
        sendEmail(xodim.getEmail(), vazifaDto.getName());
        return new ApiResponse("vazifa topshirildi",true);
    }


    public ApiResponse getVazifa(Integer xodimId){
        List<Vazifa> all = vazifaRepository.findByXodimId(xodimId);
        return new ApiResponse("VAZIFALAR",true,all);
    }

//---------- vazifa barilganligi haqida habar beruvchi method-------------
    public ApiResponse addFinished(Integer vazifaId) {
        Optional<Vazifa> optionalVazifa = vazifaRepository.findById(vazifaId);
        if (!optionalVazifa.isPresent()){
            return new ApiResponse("vazifa not found",false);
        }
        Vazifa vazifa = optionalVazifa.get();
        vazifa.setFinished(true);
        vazifaRepository.save(vazifa);
        User xodim = vazifa.getXodim();
        sendingEmail(xodim.getEmail(),vazifa.getName());
        return new ApiResponse("vazifa bajarildi",true);
    }

//    ----------VAZIFA BERISH UCHUN EMAIL----------

    public boolean sendEmail(String email,String name){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("manager@gmail.com");
            message.setTo(email);
            message.setSubject("Bajaradigan ishingiz");
            message.setText("Tasdiqlash");
            javaMailSender.send(message);
            return true;
        }catch(Exception e){
            return false;
        }
    }
//---------- vazifa bajarilganligini aytish uchun email----------
    public boolean sendingEmail(String email,String name){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("xodim@gmail.com");
            message.setTo(email);
            message.setSubject("Bajaradigan ishingiz");
            message.setText("Vazia bajarildi");
            javaMailSender.send(message);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
