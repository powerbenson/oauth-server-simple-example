package tw.idb.oauth.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idb.oauth.api.LoginApiService;
import tw.idb.oauth.vo.User;

/*
 *  此Service包含跟登入有關的操作
 *  之後可以通過注入@Service("loginService") 用裡面的方法進行登入
 */

@Service("loginService")
public class LoginService {

    @Autowired
    private LoginApiService loginApiService;

    public User login(String username, String password) {
        String user = loginApiService.callLogin(username, password);
        return new Gson().fromJson(user, User.class);
    }
}
