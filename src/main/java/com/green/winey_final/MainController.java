package com.green.winey_final;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/oauth/redirect")
    public String getRedirect() {
        return "oauth/index";
    }

    @GetMapping("/dm")
    public String getDm() {
        return "dm/index";
    }

    @GetMapping("/feed")
    public String getFeed() {
        return "feed/index";
    }

    @GetMapping("/user/profile")
    public String getUserProfile() {
        return "user/profile";
    }

    @GetMapping("/user/signin")
    public String getUserSignin() {
        return "user/signin";
    }

    @GetMapping("/user/signup")
    public String getUserSignup() {
        return "user/signup";
    }
}
