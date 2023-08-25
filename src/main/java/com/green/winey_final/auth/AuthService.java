package com.green.winey_final.auth;


import com.green.winey_final.auth.model.AuthResVo;
import com.green.winey_final.auth.model.SignInReqDto;
import com.green.winey_final.auth.model.SignOutReqDto;
import com.green.winey_final.auth.model.SignUpReqDto;
import com.green.winey_final.common.config.exception.AuthErrorCode;
import com.green.winey_final.common.config.exception.ErrorCode;
import com.green.winey_final.common.config.exception.RestApiException;
import com.green.winey_final.common.config.properties.AppProperties;
import com.green.winey_final.common.config.redis.RedisService;
import com.green.winey_final.common.config.security.AuthTokenProvider;
import com.green.winey_final.common.config.security.model.*;
import com.green.winey_final.common.entity.RegionNmEntity;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.utils.MyHeaderUtils;
import com.green.winey_final.repository.RegionNmRepository;
import com.green.winey_final.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final RedisService redisService;
    private final UserRepository userRep;
    private final AuthTokenProvider authTokenProvider;
    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;

    private final RegionNmRepository regionNmRep;


    public AuthResVo signUp(SignUpReqDto dto
            , HttpServletRequest req
            , HttpServletResponse res) {

        String ip = req.getRemoteAddr();
        log.info("local-login ip : {}", ip);

        RegionNmEntity regionNmEntity = regionNmRep.findById(dto.getRegionNmId()).get();
        log.info("RegionNmEntity : {}", regionNmEntity);

        UserEntity p = UserEntity.builder()

                .email(dto.getEmail())
                .upw(passwordEncoder.encode(dto.getUpw()))
                .unm(dto.getUnm())
                .providerType(ProviderType.LOCAL)
                .roleType(RoleType.USER)
                .tel(dto.getTel())
                .tos_yn(dto.getTos_yn())
                .del_yn(dto.getDel_yn())
                .regionNmEntity(regionNmEntity)
                .build();

        log.info("UserEntity : {}", p);
        userRep.save(p);

        return processAuth(p, req, res);
    }


    public AuthResVo signIn(SignInReqDto dto, HttpServletRequest req, HttpServletResponse res) {
        String ip = req.getRemoteAddr();
        log.info("local-login ip : {}", ip);

        UserEntity r = userRep.findByProviderTypeAndUid(ProviderType.LOCAL, dto.getUid());
        if(r == null) {
            throw new RestApiException(AuthErrorCode.NOT_FOUND_ID);
        }

        if(!passwordEncoder.matches(dto.getUpw(), r.getUpw())) {
            throw new RestApiException(AuthErrorCode.VALID_PW);
        }

        // RT가 이미 있을 경우
        String redisRefreshTokenKey = String.format("%s:%s", appProperties.getAuth().getRedisRefreshKey(), r.getUserId());
        if(redisService.getValues(redisRefreshTokenKey) != null) {
            redisService.deleteValues(redisRefreshTokenKey); // 삭제
        }

        return processAuth(r, req, res);
    }

    private AuthResVo processAuth(UserEntity userEntity, HttpServletRequest req, HttpServletResponse res) {
        LoginInfoVo vo = new LoginInfoVo(userEntity.getUserId(), new ArrayList(Arrays.asList(userEntity.getRoleType().getCode())));

        AuthToken at = authTokenProvider.createAccessToken(userEntity.getUid(), vo);
        AuthToken rt = authTokenProvider.createRefreshToken(userEntity.getUid(), vo);

        String redisRefreshTokenKey = String.format("%s:%s", appProperties.getAuth().getRedisRefreshKey(), userEntity.getUserId());
        redisService.setValuesWithTimeout(redisRefreshTokenKey, rt.getToken(), appProperties.getAuth().getRefreshTokenExpiry());

        int cookieMaxAge = (int) (appProperties.getAuth().getRefreshTokenExpiry() * 0.001);
        MyHeaderUtils.deleteCookie(req, res, REFRESH_TOKEN);
        MyHeaderUtils.addCookie(res, REFRESH_TOKEN, rt.getToken(), cookieMaxAge);

        return AuthResVo.builder().accessToken(at.getToken()).build();
    }

    public void signOut(String accessToken
            , HttpServletRequest req
            , HttpServletResponse res) {

        if(accessToken != null) {
            AuthToken authToken = new AuthToken(accessToken, appProperties.getAccessTokenKey());

            String blackAccessTokenKey = String.format("%s:%s", appProperties.getAuth().getRedisAccessBlackKey(), accessToken);
            long expiration = authToken.getTokenExpirationTime() - LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if(expiration > 0) {
                redisService.setValuesWithTimeout(blackAccessTokenKey, "logout", expiration);
            }
        }

        //cookie에서 값 가져오기
        Optional<Cookie> refreshTokenCookie = MyHeaderUtils.getCookie(req, REFRESH_TOKEN);
        if(refreshTokenCookie.isEmpty()) {
            throw new RestApiException(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        Optional<String> refreshToken = refreshTokenCookie.map(Cookie::getValue);
        if(refreshToken.isPresent()) {
            AuthToken authToken = new AuthToken(refreshToken.get(), appProperties.getRefreshTokenKey());
            long iuser = authToken.getUserDetails().getIuser();
            String redisRefreshTokenKey = String.format("%s:%s", appProperties.getAuth().getRedisRefreshKey(), iuser);
            redisService.deleteValues(redisRefreshTokenKey);
        }
        MyHeaderUtils.deleteCookie(req, res, REFRESH_TOKEN);
    }

    public AuthResVo refresh(HttpServletRequest req) {
        Optional<Cookie> refreshTokenCookie = MyHeaderUtils.getCookie(req, REFRESH_TOKEN);
        if(refreshTokenCookie.isEmpty()) {
            throw new RestApiException(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        String refreshToken = refreshTokenCookie.map(Cookie::getValue).get();

        AuthToken authToken = new AuthToken(refreshToken, appProperties.getRefreshTokenKey());
        UserPrincipal up = authToken.getUserDetails();
        LoginInfoVo vo = new LoginInfoVo(up.getIuser(), up.getRoles());

        AuthToken at = authTokenProvider.createAccessToken(up.getUid(), vo);

        return AuthResVo.builder().accessToken(at.getToken()).build();
    }
}
