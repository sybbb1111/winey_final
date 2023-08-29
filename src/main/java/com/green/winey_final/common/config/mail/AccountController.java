package com.green.winey_final.common.config.mail;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {
    private final EmailService emailService;

    @PostMapping("/api/login/mailConfirm")
    @ResponseBody
    @Operation(summary = "이메일로 인증코드 발송", description = "발송 성공 시 인증코드 리턴")
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }

    @PostMapping("/api/login/codeConfirm")
    @ResponseBody
    @Operation(summary = "이메일 인증코드 확인", description = "인증코드 일치 시 1, 불일치 시 0 리턴, 2분간만 유효")
    public String codeConfirm(@RequestParam String key) throws Exception{
        return emailService.verifyEmail(key);
    }
}
