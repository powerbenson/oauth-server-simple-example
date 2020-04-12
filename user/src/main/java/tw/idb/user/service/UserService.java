package tw.idb.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idb.user.dao.UserDao;
import tw.idb.user.vo.User;

/*
 *  跟 User 操作有關的服務
 *  之後可以通過注入@Service("userService") 用裡面的方法拿到 User 資料
 */

@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByUsernameAndPassowrd(String username, String password) {
        return userDao.getUserByUsernameAndPassowrd(username, password);
    }
}
