package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard,Integer> {
    @Query(value = "select uc from user_card uc where uc.users.id = ?1",nativeQuery = false)
    List<UserCard> getAllCardByUserId(int userId);
    @Query(value = "select count(uc) from user_card uc where uc.mycard.id = ?1",nativeQuery = false)
    Integer countUserByMyCardId(int myCardId);
    @Transactional
    @Modifying
    @Query(value = "delete from user_card uc where uc.users.id=?1 and uc.mycard.id=?2",nativeQuery = false)
    Integer deleteUserCardByUserIdAndCardId(int userId, int cardId);
}
