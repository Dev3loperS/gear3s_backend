package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Integer countByEmailAndPassword(String email, String password);

    @Query(value = "select u.roles.id from users as u where u.email = ?1",nativeQuery = false)
    Integer getRoleByEmail(String email);

    /*@Query(value = "select u.id,u.email,u.password,u.fullname,u.birthday,u.phone,u.address,u.avatar,u.lastPayment," +
                    "s.name as sex,r.name as role from users u" +
                    " left join u.sex s left join u.roles r",nativeQuery = false)
    List<Users> getAllUser();*/

    List<Users> findAll();
    Users findByEmail(String email);
    Users findById(int id);
}
