package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum RedisKeyModel {
    USERS("users"),
    ROLES("roles"),
    SEXES("sexes"),
    PRODUCTS("products"),
    PROD_POPULAR("prod_popular"),
    PROD_LATEST("prod_latest"),
    PROD_TOPSALES("prod_topsales"),
    PROD_PRICE_H2L("prod_price_high2low"),
    PROD_PRICE_L2H("prod_price_low2high");

    private String value;
    RedisKeyModel(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
