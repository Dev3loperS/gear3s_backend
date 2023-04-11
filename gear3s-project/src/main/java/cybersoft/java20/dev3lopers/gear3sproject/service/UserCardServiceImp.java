package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.MyCardDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.UserCard;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserCardRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.UserCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserCardServiceImp implements UserCardService {
    @Autowired
    UserCardRepository readUserCardByUserId;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public List<MyCardDTO> readUserCardByUserId(int userId) {
        List<MyCardDTO> myCardDTOList = new ArrayList<>();
        try {
            List<UserCard> userCardList = readUserCardByUserId.getAllCardByUserId(userId);
            for (UserCard userCard: userCardList) {
                MyCardDTO myCardDto = new MyCardDTO();

                myCardDto.setId(userCard.getMyCard().getId());
                myCardDto.setNumber(userCard.getMyCard().getNumber());
                myCardDto.setName(userCard.getMyCard().getName());
                if(userCard.getMyCard().getExpiryDate() != null){
                    myCardDto.setExpiryDate(new SimpleDateFormat("MM-yyyy").format(userCard.getMyCard().getExpiryDate()));
                }
                myCardDto.setCvv(userCard.getMyCard().getCvv());

                myCardDTOList.add(myCardDto);
            }
            LOGGER.info("Read card list of user with Id '{}' successfully",userId);
            return myCardDTOList;
        } catch (Exception e){
            LOGGER.error("Failed to read card list of user with Id '{}' : {}",userId,e.getMessage());
            return null;
        }
    }
}
