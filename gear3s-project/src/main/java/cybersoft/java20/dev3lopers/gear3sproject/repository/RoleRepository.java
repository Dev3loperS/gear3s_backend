package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles,Integer> {
    Roles findByName(String name);
}
