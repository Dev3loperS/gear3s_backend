package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.MyCardDTO;

import java.util.List;

public interface UserCardService {
    List<MyCardDTO> readUserCardByUserId(int userId);
    boolean createUserCard(int userId, MyCardDTO myCardDTO);
    boolean deleteUserCard(int userId, int myCardId);
}
