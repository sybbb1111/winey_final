package com.green.winey_final.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.winey_final.common.config.properties.AppProperties;
import com.green.winey_final.common.config.properties.CorsProperties;
import com.green.winey_final.common.config.redis.RedisService;
import com.green.winey_final.common.config.security.filter.JwtAuthenticationFilter;
import com.green.winey_final.common.config.security.handler.*;
import com.green.winey_final.common.config.security.oauth.CustomOAuth2UserService;
import com.green.winey_final.common.config.security.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.green.winey_final.common.utils.MyHeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CorsProperties corsProperties;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;

    //webSecurityCustomizer를 제외한 모든 것, 시큐리티를 거친다. 보안과 연관
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authz ->
                        authz.requestMatchers(
                                        "/favicon.ico", "/js/**", "/css/**", "/static/**", "/", "/index.html"

                                        , "/swagger.html"
                                        , "/swagger-ui/**"
                                        , "/v3/api-docs/**"
                                        , "/*/oauth2/code/*"
                                        , "/oauth2/**"
                                        , "/oauth/**"

                                        , "/images/**", "/img/**"
                                        , "/error"
                                        , "/err"
                                        ,"/main", "api/main/**"


                                        ,"/sign-api/sign-in"
                                        , "/sign-api/sign-up"
                                        , "/sign-api/exception"

                                        , "/view/**"

                                        ,"/api/mypage/findid**"
                                        ,"/api/mypage/emails/**"

                                        ,"/api/search/**", "/api/search**"
                                        ,"/admin/**" //이거해서 관리자페이지에서 새로고침시 화이트라벨 오류(401) 안뜸

                                        //,"/admin", "/admin**", "/admin/**"

                                ).permitAll()
                                .requestMatchers("**exception**") .permitAll()
                                .requestMatchers(HttpMethod.GET, "/sign-api/refresh-token").permitAll()

                                .requestMatchers("/api/recommend/**").hasAnyRole("USER","ADMIN") //맞춤와인추천

                                .requestMatchers("/api/mypage/upduser/**").hasAnyRole("USER","ADMIN") //비밀번호 변경
                                .requestMatchers("/api/mypage/delUser/**").hasAnyRole("USER","ADMIN") //탈퇴
                                .requestMatchers("/api/mypage/userinfo/**").hasAnyRole("USER","ADMIN") //회원정보


                                .requestMatchers("/api/wine/**").hasAnyRole("USER","ADMIN") //장바구니 - 회원만 이용 가능

                                .requestMatchers("/api/admin/**").hasRole("ADMIN") //관리자
                                .requestMatchers("/admin/**").hasRole("ADMIN")     //관리자

                                .requestMatchers("/api/download/**").hasRole("ADMIN")
                                .requestMatchers("/api/orderList/**").hasAnyRole("USER","ADMIN") //주문내역
//                                .requestMatchers("/api/detail/**").hasAnyRole("USER","ADMIN")  //상품상세내역 //이 상태는 로그인 해야 볼 수 있도록 하는 것
                                .requestMatchers("/api/detail/**").permitAll() ////상품상세내역. 로그인하지 않아도 볼 수 있도록 권한
                                .requestMatchers("/api/payment/**").hasAnyRole("USER","ADMIN") //결제

                                .anyRequest().permitAll()


                ) //사용 권한 체크

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 사용 X
                .httpBasic(http -> http.disable()) //UI 있는 시큐리티 설정을 비활성화
                .formLogin(http -> http.disable())



                .csrf(csrf -> csrf.disable()) //CSRF 보안이 필요 X, 쿠키와 세션을 이용해서 인증을 하고 있기 때문에 발생하는 일, https://kchanguk.tistory.com/197
                .exceptionHandling(except -> {
                    except.accessDeniedHandler(tokenAccessDeniedHandler);
                    except.authenticationEntryPoint(new RestAuthenticationEntryPoint());
                })
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization  -> authorization.baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/*/oauth2/code/*"))
                        .userInfoEndpoint(userInfo  -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository);
    }

    /*
     * Cors 설정
     * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
