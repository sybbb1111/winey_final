package com.green.winey_final.user;

import com.green.winey_final.user.model.UserRegDto;
import com.green.winey_final.user.model.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.green.winey_final.common.utils.EmailValidator.emailValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/sign-up")

    public UserVo postUser(@RequestBody UserRegDto dto) {
        if (!emailValidator(dto.getEmail())) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다. 유효한 이메일 주소를 입력해주세요.");
        }
        return service.postUser(dto);
    }

}
