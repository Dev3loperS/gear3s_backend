package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum RedisKeyModel {
    USERS("users"),
    ROLES("roles"),
    SEXES("sexes"),
    PRODUCTS("products");

    private String value;
    RedisKeyModel(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
