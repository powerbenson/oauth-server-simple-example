package tw.idb.user.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.idb.user.exception.LoginErrorException;
import tw.idb.user.service.UserService;
import tw.idb.user.vo.User;

/*
 *  有 @Controller或@RestController 的類別 是API route 的入口
 *  UserController 有一個API
 *  1. GET /login，input帶上帳號跟密碼，可以拿到 User 的相關資料
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {

        User user;

        try {
            user = userService.getUserByUsernameAndPassowrd(username, password);
            return new ResponseEntity<>(new Gson().toJson(user), HttpStatus.OK);
        } catch (LoginErrorException e) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
