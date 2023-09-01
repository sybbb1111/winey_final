package com.green.winey_final.mypage;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.mypage.model.UserRes;
import com.green.winey_final.mypage.model.UserUpdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.OnOpen;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "마이페이지")
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService service;

    @GetMapping("/userinfo")
    @Operation(summary = "로그인한 사람의 회원정보 출력", description = "로그인되어있을때 사용하셔야합니다. 탈퇴회원(del_yn=1)일 경우 정보 뜨지 않음")
    public ResponseEntity<UserRes> getUserInfo() {
        return ResponseEntity.ok(service.getUserInfo());
    }


    @PutMapping("/upduser")
    @Operation(summary = "회원정보 수정", description = "로그인되어있을때 사용하셔야합니다. 이름/전화번호/픽업지역 수정가능")
    public ResponseEntity<UserRes> putUserInfo(@RequestBody UserUpdDto dto) {
        return ResponseEntity.ok(service.putUserInfo(dto));
    }

    @PutMapping("/delUser")
    @Operation(summary = "회원 탈퇴 처리", description = "로그인되어있을때 사용하셔야합니다. 회원정보의 del_yn을 1로 수정, 탈퇴처럼 안보이게 함<br>" +
            "탈퇴처리됐을 경우 Request body 에 1로 뜸")
    public ResponseEntity<Integer> delUser() {
        service.delUser();
        return ResponseEntity.ok(1);
    }


    @GetMapping("/emails/{email}/exists")
    @Operation(summary = "로컬 가입 회원 이메일 중복체크", description = "있던 기능은 아니지만 혹시 필요할수도 있을까봐 만들어봤어용,,ㅎ,,true일 경우 이미 가입한 회원, false일 경우 가입가능(가입이력없음)")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(service.checkEmailDuplicate(email));
    }

    @GetMapping("/findid")
    @Operation(summary = "아이디 찾기", description = "이름 + 휴대폰번호 입력 -> 가입이메일+가입경로 보여줌<br>"
            + "가입시 이메일의 첫 2글자만 공개, 2글자 이후는 * 처리로 비공개")
    public ResponseEntity<String> finduser(@RequestParam String unm, @RequestParam String tel) {

        return ResponseEntity.ok(service.findUserId(unm, tel));
    }



}
