package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum RedisModel {
    ALLUSERS("users"),
    USER("user"),
    MYCARDS("mycards"),
    ROLES("roles"),
    SEXES("sexes"),
    PRODUCTS("products"),
    RATING("ratings"),
    CATEPROP("cateProps");

    private String value;
    RedisModel(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
