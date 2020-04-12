package tw.idb.user.vo;

/*
 *  使用者資料的物件
 */

public class User {

    private String userid;
    private String username;
    private String accountStatus;
    private String email;
    private String phone;
    private Long expire;

    public User(String userid, String username, String accountStatus, String email, String phone, Long expire) {
        this.userid = userid;
        this.username = username;
        this.accountStatus = accountStatus;
        this.email = email;
        this.phone = phone;
        this.expire = expire;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
