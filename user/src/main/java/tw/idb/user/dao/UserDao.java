package tw.idb.user.dao;

import org.springframework.stereotype.Repository;
import tw.idb.user.exception.LoginErrorException;
import tw.idb.user.vo.User;

/*
 *  @Repository 表示跟數據層有關
 *  UserDao 是跟 User 有關的數據層操作
 */

@Repository
public class UserDao {

    public User getUserByUsernameAndPassowrd(String username, String password) {

        // 這段原本是寫查詢DB資料的地方，沒有建DB用hard code dummy的資料代替
        if (username.equals("amazingtalker") && password.equals("12345")) {
            User user = new User("123456", "amazingtalker", "PAYING", "amazingtalker@gmail.com", "0900000000", Long.valueOf("1686590302274"));
            return user;
        } else {
            throw new LoginErrorException("username or password not correct");
        }
    }
}
