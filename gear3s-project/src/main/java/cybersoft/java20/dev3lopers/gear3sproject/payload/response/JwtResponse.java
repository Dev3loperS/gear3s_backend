package cybersoft.java20.dev3lopers.gear3sproject.payload.response;

public class JwtResponse {
    private int userId;
    private String email;
    private String fullname;
    private String avatar;
    private String role;
    private String newToken;
    private long issueDate;
    private long expiryDate;

    public JwtResponse() {
    }

    public JwtResponse(int userId, String email, String fullname, String avatar, String role, String newToken, long issueDate, long expiryDate) {
        this.userId = userId;
        this.email = email;
        this.fullname = fullname;
        this.avatar = avatar;
        this.role = role;
        this.newToken = newToken;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

    public long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(long issueDate) {
        this.issueDate = issueDate;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }
}
