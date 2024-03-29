package com.ssafy.campfire.global.oauth2.service;

import com.ssafy.campfire.board.repository.BoardRepository;
import com.ssafy.campfire.bootcamp.domain.Bootcamp;
import com.ssafy.campfire.bootcamp.repository.BootcampRepository;
//import com.ssafy.campfire.global.login.PrincipalDetails;
import com.ssafy.campfire.global.oauth2.PrincipalDetails;

import com.ssafy.campfire.global.oauth2.GoogleUserInfo;
import com.ssafy.campfire.global.oauth2.KakaoUserInfo;
import com.ssafy.campfire.global.oauth2.NaverUserInfo;
import com.ssafy.campfire.global.oauth2.OAuth2UserInfo;
import com.ssafy.campfire.user.domain.User;
import com.ssafy.campfire.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final BootcampRepository bootcampRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo( oAuth2User.getAttributes() );
        } else if(provider.equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo( (Map)oAuth2User.getAttributes() );
        } else if(provider.equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo( (Map)oAuth2User.getAttributes().get("response") );
        }

        String email = oAuth2UserInfo.getEmail();
        int index = email.indexOf("@");
        String userId = email.substring(0,index);
        String nickname = userId+"_"+provider;

        Optional<User> optionalUser = userRepository.findByEmailAndProvider(email,provider);
        Optional<Bootcamp> Optionalbootcamp = bootcampRepository.findById(1L);

        User user = null;
        Bootcamp bootcamp = Optionalbootcamp.get();
        if(optionalUser.isEmpty()) {
            user = new User(nickname, email, provider, bootcamp);
            userRepository.save(user);
        } else {
            user = optionalUser.get();
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}