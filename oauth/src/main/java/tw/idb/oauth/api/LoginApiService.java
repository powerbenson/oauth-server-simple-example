package tw.idb.oauth.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 *  @FeignClient 使用註解跟標記的方式，結合了 httpClient
 *  ${idb.domain.apiserver} 是要call的API的domain
 *  callLogin會去call User Server的登入API
 */

@FeignClient(value = "loginApiService", url = "${idb.domain.apiserver}")
public interface LoginApiService {

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String callLogin(@RequestParam String username,
                            @RequestParam String password);
}
