package com.ssafy.campfire.global.oauth2.handler;

import com.ssafy.campfire.global.jwt.service.JwtService;
import com.ssafy.campfire.global.oauth2.CustomOAuth2User;
import com.ssafy.campfire.global.oauth2.PrincipalDetails;
import com.ssafy.campfire.user.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
//@Transactional
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        //CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        loginSuccess(request, response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletRequest request,HttpServletResponse response, PrincipalDetails oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getName());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, "Bearer " +accessToken, "Bearer " +refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getName(), refreshToken);

        // redirectStrategy.sendRedirect(request, response,"localhost:3000/login/info");
        //redirectStrategy.sendRedirect(request, response,"http://i9a408.p.ssafy.io:3000/loginInfo?token="+accessToken+"refresh="+refreshToken);
    }
}
