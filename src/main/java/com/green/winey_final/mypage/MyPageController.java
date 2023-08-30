package com.green.winey_final.mypage;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.mypage.model.UserRes;
import com.green.winey_final.mypage.model.UserUpdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary = "로그인한 사람의 회원정보 출력", description = "로그인되어있을때 사용하셔야합니다. 탈퇴회원(del_yn)일 경우 정보 뜨지 않음")
    public ResponseEntity<UserRes> getUserInfo() {
        return ResponseEntity.ok(service.getUserInfo());
    }


    @PutMapping("/upduser")
    @Operation(summary = "회원정보 수정", description = "로그인되어있을때 사용하셔야합니다. 이름/전화번호/픽업지역 수정가능")
    public ResponseEntity<UserRes> putUserInfo(@RequestBody UserUpdDto dto) {
        return ResponseEntity.ok(service.putUserInfo(dto));
    }

    @PutMapping("/deluser")
    @Operation(summary = "회원 탈퇴 처리", description = "로그인되어있을때 사용하셔야합니다. 회원정보의 del_yn을 1로 수정, 탈퇴처럼 안보이게 함<br>" +
            "탈퇴처리될 경우 Request body 에 1로 뜸")
    public ResponseEntity<Integer> delUser() {
        service.delUser();
        return ResponseEntity.ok(1);

    }








}
