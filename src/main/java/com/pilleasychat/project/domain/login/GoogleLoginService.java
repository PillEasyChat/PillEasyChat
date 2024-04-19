package com.pilleasychat.project.domain.login;

import com.pilleasychat.project.domain.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GoogleLoginService {

    User getGoogleAccessToken(String accessCode);

    Map<String, String> extractNameAndEmail(String json);

    User login(Map<String, String> userInfo);
}
