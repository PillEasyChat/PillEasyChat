package com.pilleasychat.project.domain.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.signup.SignupService;
import com.pilleasychat.project.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleLoginServiceImpl implements GoogleLoginService{

    private final UserService userService;
    private final SignupService signupService;
    private final LoginService loginService;

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    @Value("${spring.security.oauth2.client.registration.google.client_id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client_secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect_uri}")
    private String LOGIN_REDIRECT_URL;
    @Override
    public User getGoogleAccessToken(String accessCode) {

        RestTemplate restTemplate=new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", LOGIN_REDIRECT_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity=restTemplate.postForEntity(GOOGLE_TOKEN_URL, params,String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            try {
                JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
                String[] idToken = jsonNode.get("id_token").asText().split("\\.");
                String decodedInfo = decryptBase64UrlToken(idToken[1]);
                System.out.println(extractNameAndEmail(decodedInfo));
                return login(extractNameAndEmail(decodedInfo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static String decryptBase64UrlToken(String jwtToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(jwtToken));
    }

    @Override
    public Map<String, String> extractNameAndEmail(String json) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            String name = jsonNode.get("name").asText();
            String email = jsonNode.get("email").asText();
            resultMap.put("name", name);
            resultMap.put("email", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public User login(Map<String, String> userInfo){
        User user = userService.findByEmail(userInfo.get("email"));
        // 회원가입
        if (user == null)
            user = signupService.createUser(
                    userInfo.get("email"), userInfo.get("name"));
        //로그인
        loginService.login(user.getEmail(), user.getPassword());

        return user;
    }
}
