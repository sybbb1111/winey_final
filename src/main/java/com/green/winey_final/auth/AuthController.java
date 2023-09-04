package com.green.winey_final.auth;

import com.green.winey_final.auth.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입, 로그인, 로그아웃, 토큰발급")
@Slf4j
@RestController
@RequestMapping("/sign-api")
@RequiredArgsConstructor
public class AuthController {
    @PostMapping("/sign-up")
    @Operation(summary = "로컬 회원가입", description = "userId = 회원정보 PK값<br>" +
            "-------------------------<br>" +
            "email = 회원가입 이메일<br>" +
            "upw = 비밀번호<br>" +
            "unm = 이름<br>" +
            "tel = 전화번호(01011112222 식으로 - 없이 숫자 11자 입력)<br>" +
            "regionNmId = 픽업 선택 지역 PK값 (1:서울 / 2:부산 / 3:대구 / 4:인천 / 5:광주 / 6:대전 / 7:울산 / 8: 세종특별자치시 / 9:경기도 / 10:강원특별자치도 / 11:충청북도 / 12:충청남도 / 13:전라북도 / 14:전라남도 / 15:경상북도 / 16:경상남도 / 17:제주특별자치도)<br>" +
            "------------------------- 여기까지는 입력필요 값, 아래는 데이트베이스에 등록된 값이에용 참고부탁드립니당<br>" +

            "uid = 소셜로그인 가입회원일 경우 데이터베이스로 넘어오는 랜덤한 스트링 값<br>" +
            "roleType = 고객일 경우 USER, 관리자일 경우 ADMIN, 가입시 자동으로 USER로 들어가게끔 세팅해놨습니다. 관리자는 회원가입없이 따로 생성<br>" +
            "providerType = 와이니 가입회원일 경우 LOCAL, 소셜일 경우 해당 사이트(KAKAO)<br>" +
            "tosYn = 동의여부, 0이면 비동의 1이면 동의, 가입시 자동으로 1로 들어가게끔 세팅해놨습니다<br>" +
            "delYn = 탈퇴여부, 0이면 비탈퇴 1이면 탈퇴, 로그인 시 자동으로 0으로 들어가도록 세팅해놨습니다<br>")


    public ResponseEntity<AuthResVo> postSignUp(@RequestBody SignUpReqDto dto
            , HttpServletRequest req
            , HttpServletResponse res) {
        AuthResVo vo = service.signUp(dto, req, res);
        log.info("Dto : {}", dto);
        return ResponseEntity.ok(vo);
    }

    private final AuthService service;

    @PostMapping("/sign-in")
    @Operation(summary = "로컬 로그인", description = "RuleType : 사용자일시 USER, 관리자일 시 ADMIN 으로 뜸")
    public ResponseEntity<AuthResLoginVo> postSignIn(@RequestBody SignInReqDto dto
            , HttpServletRequest req
            , HttpServletResponse res) {
        AuthResLoginVo vo = service.signIn(dto, req, res);
        return ResponseEntity.ok(vo);
    }



    @GetMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity getSignout(@RequestParam(required = false) String accessToken
            , HttpServletRequest req
            , HttpServletResponse res) {
        service.signOut(accessToken, req, res);
        return ResponseEntity.ok(1);
    }

    @GetMapping("/refresh-token")
    @Operation(summary = "리프레쉬 토큰으로 액세스 토큰 재발급 자동화")
    public ResponseEntity<AuthResVo> getRefresh(HttpServletRequest req) {
        AuthResVo vo = service.refresh(req);
        return ResponseEntity.ok(vo);
    }




}
