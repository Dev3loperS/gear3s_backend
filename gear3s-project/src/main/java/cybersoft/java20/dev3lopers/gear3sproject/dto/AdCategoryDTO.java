package cybersoft.java20.dev3lopers.gear3sproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdCategoryDTO {

    int  id ;

    String name ;

    public AdCategoryDTO() {
    }

    public AdCategoryDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
