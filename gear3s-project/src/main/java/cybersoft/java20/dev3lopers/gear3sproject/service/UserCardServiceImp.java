package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.MyCardDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.MyCard;
import cybersoft.java20.dev3lopers.gear3sproject.entity.UserCard;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.MyCardRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserCardRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.UserCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserCardServiceImp implements UserCardService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCardRepository userCardRepository;

    @Autowired
    MyCardRepository myCardRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public List<MyCardDTO> readUserCardByUserId(int userId) {
        List<MyCardDTO> myCardDTOList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.MYCARDS.getValue());
            if (data == null){
                List<UserCard> userCardList = userCardRepository.getAllCardByUserId(userId);
                for (UserCard userCard: userCardList) {
                    MyCardDTO myCardDto = new MyCardDTO();

                    myCardDto.setId(userCard.getMyCard().getId());
                    myCardDto.setNumber(userCard.getMyCard().getNumber());
                    myCardDto.setName(userCard.getMyCard().getName());
                    if(userCard.getMyCard().getExpiryDate() != null){
                        myCardDto.setExpiryDate(new SimpleDateFormat("MM-yyyy").format(userCard.getMyCard().getExpiryDate()));
                    }
                    myCardDto.setCvv("***");

                    myCardDTOList.add(myCardDto);
                }
                redisTemplate.opsForValue().set(RedisModel.MYCARDS.getValue(),gson.toJson(myCardDTOList));
                redisTemplate.expire(RedisModel.MYCARDS.getValue(),30, TimeUnit.MINUTES);
            } else {
                myCardDTOList = gson.fromJson(data,new TypeToken<List<MyCardDTO>>(){}.getType());
            }
            LOGGER.info("Read card list of user with Id '{}' successfully",userId);
            return myCardDTOList;
        } catch (Exception e){
            LOGGER.error("Failed to read card list of user with Id '{}' : {}",userId,e.getMessage());
            return null;
        }
    }

    @Override
    public boolean createUserCard(int userId, MyCardDTO myCardDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        try {
            MyCard myCard = new MyCard();
            myCard.setNumber(myCardDTO.getNumber());
            myCard.setName(myCardDTO.getName());
            myCard.setExpiryDate(new SimpleDateFormat("MM-yyyy").parse(myCardDTO.getExpiryDate()));
            myCard.setCvv(bCryptPasswordEncoder.encode(myCardDTO.getCvv()));
            myCardRepository.save(myCard);
            redisTemplate.delete(RedisModel.MYCARDS.getValue());

            Users user = userRepository.findById(userId);
            if (user != null){
                UserCard userCard = new UserCard();
                userCard.setUsers(user);
                userCard.setMyCard(myCard);
                userCardRepository.save(userCard);
                LOGGER.info("Added new card for account '{}' successfully,",user.getEmail());
                return true;
            }
            LOGGER.error("Account with Id '{}' does not exist to add new card",userId);
            return false;
        } catch (Exception e){
            LOGGER.error("Failed to add new user card for account with Id '{}' : {}",userId,e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUserCard(int userId, int myCardId) {
        try {
            if(userCardRepository.deleteUserCardByUserIdAndCardId(userId,myCardId) > 0){
                redisTemplate.delete(RedisModel.MYCARDS.getValue());

                if(userCardRepository.countUserByMyCardId(myCardId) < 1){
                    myCardRepository.deleteById(myCardId);
                    LOGGER.info("Deleted card with Id '{}' successfully,",myCardId);
                }
                LOGGER.info("Deleted user card has Id '{}' of account with Id '{}' successfully,",myCardId,userId);
                return true;
            }
            LOGGER.error("Account with Id '{}' and card with Id '{}' do not match",userId,myCardId);
            return false;
        } catch (Exception e){
            LOGGER.error("Failed to delete user card has Id '{}' of account with Id '{}' : {}",myCardId,userId,e.getMessage());
            return false;
        }
    }


}
