package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class ProdRatingDTO {
    private int ratingId;
    private int productId;
    private String productName;
    private int userId;
    private String userName;
    private String userAvatar;
    private byte star;
    private String comment;

    public ProdRatingDTO() {
    }

    public ProdRatingDTO(int ratingId, int productId, String productName, int userId, String userName, String userAvatar, byte star, String comment) {
        this.ratingId = ratingId;
        this.productId = productId;
        this.productName = productName;
        this.userId = userId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.star = star;
        this.comment = comment;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public byte getStar() {
        return star;
    }

    public void setStar(byte star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
