package uz.pdp.task_2_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_5.entity.User;
import uz.pdp.task_2_5.entity.Vazifa;

import java.util.List;
import java.util.Optional;

public interface VazifaRepository extends JpaRepository<Vazifa,Integer> {
    List<Vazifa> findByXodimId(Integer xodim_id);
}
