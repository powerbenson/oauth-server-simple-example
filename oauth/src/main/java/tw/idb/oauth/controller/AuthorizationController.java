package tw.idb.oauth.controller;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tw.idb.oauth.service.AuthorizeCodeService;
import tw.idb.oauth.service.ClientService;
import tw.idb.oauth.service.LoginService;
import tw.idb.oauth.vo.OauthUser;
import tw.idb.oauth.vo.User;

/*
 *  有 @Controller或@RestController 的類別 是API route 的入口
 *  AuthorizationController 有兩個API
 *  1. GET /authorization，登入頁的入口
 *  2. POST /authorization，送出帳密後帶上code，並且redirect第一個API之前帶過來的redirect_url
 */

@Controller
@RequestMapping("/oauth")
public class AuthorizationController {

    @Autowired
    @Qualifier("authorizeCodeService")
    private AuthorizeCodeService authorizeCodeService;

    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    @Autowired
    @Qualifier("loginService")
    private LoginService loginService;

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String oauth_login_page(
            Model model,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "response_type", required = false) String responseType,
            @RequestParam(value = "redirect_url", required = false) String redirectUrl,
            @RequestParam(value = "state", required = false) String state) {

        if (!clientService.compareClientIdAndRedirectUrl(clientId, redirectUrl)) {
            return "404Page";
        }

        model.addAttribute("redirect_url", redirectUrl);
        model.addAttribute("response_type", responseType);
        model.addAttribute("client_id", clientId);
        model.addAttribute("state", state);

        return "login";
    }


    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public String oauth_login(
            Model model,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "redirect_url", required = false) String redirectUrl,
            @RequestParam(value = "response_type", required = false) String responseType,
            @RequestParam(value = "state", required = false) String state) {

        if (!clientService.compareClientIdAndRedirectUrl(clientId, redirectUrl)) {
            return "404Page";
        }

        try {
            User user = loginService.login(username, password);
            return "redirect:" + redirectUrl + "?code=" + authorizeCodeService.createAuthorizationCodeAndStore(new OauthUser(user, clientId)) + "&state=" + state;
        } catch (FeignException.BadRequest e) {
            return "login_error";
        } catch (FeignException.Unauthorized e) {
            return "login_error";
        } catch (FeignException.InternalServerError e) {
            return "login_error";
        } catch (Exception e) {
            return "login_error";
        }
    }
}
