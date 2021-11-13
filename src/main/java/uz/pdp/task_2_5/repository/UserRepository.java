package uz.pdp.task_2_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_5.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

    Optional<User> findByEmail(String email);
}
