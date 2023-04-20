package cybersoft.java20.dev3lopers.gear3sproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class UserCardDTO {
    @JsonIgnore
    private int id ;
    private User_OrderDTO users;


    @JsonIgnore
    @JsonDeserialize
    private MyCardDTO myCard ;

    public UserCardDTO() {
    }

    public UserCardDTO(int id, User_OrderDTO users, MyCardDTO myCard) {
        this.id = id;
        this.users = users;
//        this.myCard = myCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User_OrderDTO getUsers() {
        return users;
    }

    public void setUsers(User_OrderDTO users) {
        this.users = users;
    }

//    @JsonProperty
    public MyCardDTO getMyCard() {
        return myCard;
    }

//    @JsonProperty
    public void setMyCard(MyCardDTO myCard) {
        this.myCard = myCard;
    }
}
