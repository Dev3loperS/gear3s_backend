package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.MyCard;
import cybersoft.java20.dev3lopers.gear3sproject.entity.UserCard;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard,Integer> {
    @Query(value = "select uc from user_card uc where uc.users.id = ?1",nativeQuery = false)
    List<UserCard> getAllCardByUserId(int userId);
    @Query(value = "select count(uc) from user_card uc where uc.mycard.id = ?1",nativeQuery = false)
    Integer countUserByMyCardId(int myCardId);
    @Transactional
    @Modifying
    @Query(value = "delete from user_card uc where uc.users.id=?1 and uc.mycard.id=?2",nativeQuery = false)
    Integer deleteUserCardByUserIdAndCardId(int userId, int cardId);

    @Query(value = "select  uc from user_card uc inner join users u on uc.users.id = u .id where uc.users.id = ?1 limit 1",nativeQuery = true)
    UserCard findUserCardByUserId(int id );

    @Query(value = "select  uc from user_card uc inner join users u on uc.users.id  = u.id inner join mycard m on m.id = uc.mycard.id where uc.users.id = ?1 and uc.mycard.id =?2")
    UserCard findByUserIdAndMycardId(int uId,int mId  ) ;

    UserCard findById(int id ) ;
}

