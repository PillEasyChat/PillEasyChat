package com.pilleasychat.project.domain.login;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Slf4j
@Service
public class KakaoLoginServiceImpl implements KakaoLoginService{
    @Override
    public String getAccessTokenFromKakao(String client_id, String code) throws IOException {
        String reqURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="+client_id+"&code=" + code;
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");


        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
        });

        log.info("Response Body : " + result);

        String accessToken = (String) jsonMap.get("access_token");
        String refreshToken = (String) jsonMap.get("refresh_token");
        String scope = (String) jsonMap.get("scope");


        log.info("Access Token : " + accessToken);
        log.info("Refresh Token : " + refreshToken);
        log.info("Scope : " + scope);


        return accessToken;
    }
}
