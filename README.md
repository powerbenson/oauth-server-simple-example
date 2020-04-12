# Oauth 2.0 Authorization Server 簡單範例說明

## Authorization Code Grant


    +----------+                                    +----------+
    | Resource |                                    |   User   |
    |   Owner  |                                    |  Server  |
    |          |                                    |          |
    +----------+                                    +----------+
         ^                                               ^
         |                                               |
        (B)                                             (B)
    +----|-----+          Client Identifier      +-------+-------+
    |         -+----(A)-- & Redirection URL ---->|               |
    |  User-   |                                 | Authorization |
    |  Agent  -+----(B)-- User authenticates --->|     Server    |
    |          |                                 |               |
    |         -+----(C)-- Authorization Code ---<|               |
    +-|----|---+                                 +---------------+
      |    |                                         ^      v
     (A)  (C)                                        |      |
      |    |                                         |      |
      ^    v                                         |      |
    +---------+                                      |      |
    |         |>---(D)-- Authorization Code ---------'      |
    |  Client |          & Redirection URL                  |
    |         |                                             |
    |         |<---(E)----- Access Token -------------------'
    +---------+       (w/ Optional Refresh Token)
      v    ^
      |    |
     (F)  (G)                                       +-----------+
      |    +------------ Leet Code Answer -------- <| leet code |
      |                                             |    API    |
      +---------- Carry Access Token in Header ---->|   Server  |
                                                    +-----------+

註: (A), (B), (C) 這三步的線拆成兩段，因為會經過 user-agent 跟 User Server

## 目錄結構

[這個專案的 GitHub 網址](https://github.com/powerbenson/oauth-server-simple-example)

+ leetcode -> leet code API Server 程式碼 (透過call api 拿到解答，但在這之前要先拿到access_token)，解答程式碼檔案的路徑為 /leetcode/src/main/java/tw/idb/leetcode/service/AlphabetBoardPath.java
+ oauth -> Authorization Server 程式碼 (對Client進行認證，已經幫amaingtalker註冊了，client_id=amazingtalker、client_secret=amazingtalker-secret)
+ user -> User Server 程式碼 (對帳號密碼進行認證)
+ img -> img (放README.md的圖)
+ postman -> (放postman匯出的檔案，有/token、/alphabet-Board-Path兩隻API，記得要換掉code跟token)


## 操作步驟如下

+ (A)  使用瀏覽器call以下網址 http://34.80.244.232/oauth/v1.0/authorization?client_id=amazingtalker&response_type=code&redirect_url=https://developers.google.com/oauthplayground&state=
+ (B) 輸入帳號密碼 amazingtalker/12345
<img src="./img/login_page.png" style="zoom:80%" />
+ (C) 瀏覽器會redirect到(A)填的redirect_url，並且帶上code，把他複製下來，如下圖
<img src="./img/Authorization_Code.png" style="zoom:80%" />
+ (D) 使用code去換access token，參考下方圖


參數名       | 必/選	| 填什麼 
-------------|-------|--------------------------------------------
grant_type   | 必    | authorization_code
code         | 必    | 在 (C) 拿到的 Authorization Code
redirect_url | 必    | 跟 (A) 一樣是https://developers.google.com/oauthplayground
client_id    | 必    | amazingtalker
client_secret| 必    | amazingtalker-secret

    POST /oauth/v1.0/token HTTP/1.1
    Host: 34.80.244.232
    Content-Type: application/x-www-form-urlencoded

    grant_type=authorization_code
    &code=STj9lixt3lxz0iLfK9bXZgDd7kEQklul
    &redirect_url=https://developers.google.com/oauthplayground
    &client_id=amazingtalker
    &client_secret=amazingtalker-secret

+ (E) Authorization Server會返回access_token跟refresh_token

如下

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Cache-Control: no-store
    Pragma: no-cache

    {
      "access_token":"a50f0d2d-6b70-40af-8291-cb9a83760e6d",
      "expires_in":604800,
      "token_type":"Bearer",
      "refresh_token":"b74bf35a-f722-4a8e-ac15-e452fe02473e",
      "additionalInformation": {}
    }

+ (F) 將在 (E) 步驟拿到的access_token夾帶在header call leet API Server

參數名       | 必/選	| 填什麼 
-------------|-------|--------------------------------------------
target       | 必    | 填寫英文字串
code         | 必    | 在 (E) 拿到的 access_token

    GET /leetcode/v1.0/alphabet-Board-Path?target=leetcode HTTP/1.1
    Host: 34.80.244.232
    Authorization: Bearer 3dfc5073-ef78-4ba2-970a-d8febcc61011

+ (G) 返回alphabet-Board-Path的解答
