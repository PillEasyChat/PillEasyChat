package com.pilleasychat.project.domain.login;

import java.io.IOException;
import java.util.HashMap;

public interface KakaoLoginService {
    String getAccessTokenFromKakao(String client_id, String code) throws IOException;
    HashMap<String, Object> getUserInfo(String accessToken) throws IOException;
}
