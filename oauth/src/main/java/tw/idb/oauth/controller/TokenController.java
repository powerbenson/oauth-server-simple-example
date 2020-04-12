package tw.idb.oauth.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.idb.oauth.exception.OauthCodeErrorException;
import tw.idb.oauth.service.AuthorizeCodeService;
import tw.idb.oauth.service.ClientService;
import tw.idb.oauth.service.OauthTokenService;
import tw.idb.oauth.vo.OauthToken;
import tw.idb.oauth.vo.OauthUser;

/*
 *  有 @Controller或@RestController 的類別 是API route 的入口
 *  TokenController 有一個API
 *  1. GET /token，帶上之前拿到的code，可以拿到access_token
 */

@RestController
@RequestMapping("/oauth")
public class TokenController {

    @Autowired
    @Qualifier("authorizeCodeService")
    private AuthorizeCodeService authorizeCodeService;

    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    @Autowired
    @Qualifier("oauthTokenService")
    private OauthTokenService oauthTokenService;

    @RequestMapping(value = "/token", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> token(
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "client_secret", required = false) String clientSecret,
            @RequestParam(value = "grant_type", required = false) String granType,
            @RequestParam(value = "redirect_url", required = false) String redirectUrl,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "refresh_token", required = false) String refreshToken) {

        OauthToken token;
        if (!clientService.compareCliendIdAndClientSecretAndRedirectUrl(clientId, clientSecret, redirectUrl)) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        try {
            if ("authorization_code".equals(granType)) {
                OauthUser oauthUser = authorizeCodeService.consumeAuthorizationCode(code);

                if (oauthUser == null) throw new OauthCodeErrorException();
                if (!clientId.equals(oauthUser.getClientId())) throw new OauthCodeErrorException();

                token = oauthTokenService.createOauhToken(oauthUser);
            } else if ("refresh_token".equals(granType)) {
                token = oauthTokenService.refreshToken(refreshToken);
            } else {
                return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(new Gson().toJson(token), HttpStatus.OK);
        } catch (OauthCodeErrorException e) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
