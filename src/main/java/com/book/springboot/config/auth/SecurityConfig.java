package com.book.springboot.config.auth;

import com.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAUth2UserService customOAUth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // h2-console 사용을 위해 해당 옵션은 disable
    http.csrf().disable()
        .headers().frameOptions().disable()
        .and()
        // URL 별 권한 관리 설정 옵션의 시작점
          .authorizeRequests()
        // 이 URL들은 permitAll(전체 열람 권한)
          .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")
            .permitAll()
        // 이 URL은 hasRole(USER 권한)
          .antMatchers("/api/v1/**")
            .hasRole(Role.USER.name())
        // 나머지 URL들은 모두 인증된 사용자들에게만 허용
          .anyRequest()
            .authenticated()
        .and()
          .logout()
            .logoutSuccessUrl("/")
        .and()
        // OAuth2 로그인 기능에 대한 설정 진입점
          .oauth2Login()
        // 로그인 성공 이후 사용자 정보를 가죠올 떄의 설정을 담당
            .userInfoEndpoint()
        // 로그인 성공 시 후속 조치
              .userService(customOAUth2UserService);
  }
}
