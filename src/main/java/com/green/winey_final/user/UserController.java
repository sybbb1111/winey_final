package com.green.winey_final.user;

import com.green.winey_final.user.model.UserRegDto;
import com.green.winey_final.user.model.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping
    public UserVo postUser(@RequestBody UserRegDto dto) {
        return service.postUser(dto);
    }

}
