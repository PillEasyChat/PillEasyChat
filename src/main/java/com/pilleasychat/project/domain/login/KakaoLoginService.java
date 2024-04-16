package com.pilleasychat.project.domain.login;

import java.io.IOException;

public interface KakaoLoginService {
    String getAccessTokenFromKakao(String client_id, String code) throws IOException;
}
