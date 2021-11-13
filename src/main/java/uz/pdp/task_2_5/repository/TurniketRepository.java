package uz.pdp.task_2_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_5.entity.Turniket;
import uz.pdp.task_2_5.entity.User;

import java.util.Optional;

public interface TurniketRepository extends JpaRepository<Turniket,Integer> {
}
