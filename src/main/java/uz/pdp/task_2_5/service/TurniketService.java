package uz.pdp.task_2_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2_5.entity.Turniket;
import uz.pdp.task_2_5.entity.User;
import uz.pdp.task_2_5.payload.ApiResponse;
import uz.pdp.task_2_5.payload.TurniketDto;
import uz.pdp.task_2_5.repository.TurniketRepository;
import uz.pdp.task_2_5.repository.UserRepository;

import java.util.Optional;

@Service
public class TurniketService {

    @Autowired
    TurniketRepository turniketRepository;

    @Autowired
    UserRepository userRepository;

    //    ------ turniket qo'shish----------
    public ApiResponse addTurniket(TurniketDto turniketDto){
        Turniket turniket = new Turniket();
        Optional<User> optionalUser = userRepository.findById(turniketDto.getUsername());
        if (!optionalUser.isPresent()){
            return new ApiResponse("xodim topilmadi",false);
        }
        turniket.setUsername(optionalUser.get());
        turniket.setComingDate(turniketDto.getComingDate());
        turniket.setGoingDate(turniketDto.getGoingDate());
        turniket.setOylik(turniketDto.getOylik());
        turniketRepository.save(turniket);
        return new ApiResponse("turniket qo'shildi",true);
    }
//--------- turniket ishga kelgana vaqtini kiritish uchun---------
    public ApiResponse turniketKeldi(Integer id,TurniketDto turniketDto) {
        Optional<Turniket> optionalTurniket = turniketRepository.findById(id);
        Turniket turniket = optionalTurniket.get();
        turniket.setComingDate(turniketDto.getComingDate());
        turniketRepository.save(turniket);
        return new ApiResponse("Xodim ishga keldi",true);
    }

    public ApiResponse turniketKetdi(Integer id,TurniketDto turniketDto) {
        Optional<Turniket> optionalTurniket = turniketRepository.findById(id);
        Turniket turniket = optionalTurniket.get();
        turniket.setGoingDate(turniketDto.getGoingDate());
        turniket.setOylik(turniket.getOylik()+90000.00);
        turniketRepository.save(turniket);
        return new ApiResponse("Xodim ishxondan chiqdi",true);
    }
}
