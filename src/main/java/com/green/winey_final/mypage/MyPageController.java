package com.green.winey_final.mypage;

import com.green.winey_final.mypage.model.SelUserVo;
import com.green.winey_final.mypage.model.UpduserDto2;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageService service;

    @PatchMapping("/user-correction")
    @Operation(summary = "회원정보수정", description =
            "주의사항: 로그인되어있을때 사용하셔야합니다. <br>"
    )
    public  int putUser(@RequestBody UpduserDto2 dto){
        return service.updUser(dto);
    }

    @GetMapping("/user-info")
    @Operation(summary = "로그인한사람의 회원정보",description =
            "주의사항: 로그인되어있을때 사용하셔야합니다. delYn값이 0이여야합니다. 1이면 삭제처리유저입니다. <br>")
    public SelUserVo getUser(Long userId){
        return service.selUser(userId);
    }


    @DeleteMapping("/user-secession")
    @Operation(summary = "유저 삭제 처리",description =
            "주의사항: 로그인되어있을때 사용하셔야합니다. delYn이 0에서 1로 삭제처리로 바꾸는 작업입니다.<br>")
    public int delUser(Long userId){
        return service.delUser(userId);
    }


}
