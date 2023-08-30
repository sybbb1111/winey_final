package com.green.winey_final.mypage;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.mypage.model.UserRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이페이지")
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService service;

    @GetMapping("/userinfo")
    @Operation(summary = "회원정보")
    public ResponseEntity<UserRes> getUserInfo() {
        return ResponseEntity.ok(service.getUserInfo());
    }



}
