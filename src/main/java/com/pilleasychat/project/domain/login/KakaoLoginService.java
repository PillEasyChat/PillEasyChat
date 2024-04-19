package com.pilleasychat.project.domain.login;

import com.pilleasychat.project.domain.entity.User;

import java.io.IOException;
import java.util.HashMap;

public interface KakaoLoginService {
    String getAccessTokenFromKakao(String client_id, String code) throws IOException;
    User getUserInfo(String accessToken) throws IOException;

    User login(HashMap<String, Object> userInfo);

}