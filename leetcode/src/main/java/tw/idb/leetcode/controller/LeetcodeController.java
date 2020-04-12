package tw.idb.leetcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idb.leetcode.exception.BadRequestException;
import tw.idb.leetcode.exception.UnauthorizedException;
import tw.idb.leetcode.service.AlphabetBoardPath;
import tw.idb.leetcode.service.TokenService;

/*
 *  有 @Controller或@RestController 的類別 是API route 的入口
 *  LeetcodeController 有一個API
 *  1. GET /alphabet-Board-Path，根據input targer帶的英文字串，會回對應的解答
 */

@RestController
@RequestMapping("leetcode")
public class LeetcodeController {

    @Autowired
    @Qualifier("tokenService")
    private TokenService tokenService;

    @Autowired
    @Qualifier("alphabetBoardPath")
    private AlphabetBoardPath alphabetBoardPath;

    @RequestMapping(value = "/alphabet-Board-Path", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> login(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam(value = "target", required = false) String target) {

        try {
            if (!tokenService.isToken(token)) {
                throw new UnauthorizedException();
            }

            return new ResponseEntity<>(alphabetBoardPath.alphabetBoardPath(target), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
