package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard,Integer> {
    @Query(value = "select uc from user_card as uc where uc.users.id = ?1",nativeQuery = false)
    List<UserCard> getAllCardByUserId(int userId);
}
