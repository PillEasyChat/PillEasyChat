package com.pilleasychat.project.domain.login;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GoogleLoginService {

    Map<String, String> getGoogleAccessToken(String accessCode);
}
