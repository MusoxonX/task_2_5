package uz.pdp.task_2_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2_5.entity.Role;
import uz.pdp.task_2_5.entity.q.RoleName;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName roleName);
}
